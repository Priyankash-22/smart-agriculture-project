
import os
import json
import torch
import torch.nn as nn
import torchvision.transforms as transforms

from PIL import Image
from torchvision.models import mobilenet_v2
from transformers import pipeline


# --------------------------------
# 1. Existing Hugging Face model
# --------------------------------

MODEL_NAME = "neghani/plantvillage-mobilenetv2"

print("Loading existing plant disease AI model...")

classifier = pipeline(
    "image-classification",
    model=MODEL_NAME,
    device=-1
)

print("Existing plant disease model loaded!")


# --------------------------------
# 2. Load the new Rice model
# --------------------------------

BASE_DIR = os.path.dirname(os.path.abspath(__file__))
MODEL_DIR = os.path.join(BASE_DIR, "models")

RICE_MODEL_PATH = os.path.join(
    MODEL_DIR, "rice_disease_model.pth"
)

METADATA_PATH = os.path.join(
    MODEL_DIR, "rice_model_metadata.json"
)

device = torch.device("cpu")

with open(METADATA_PATH, "r") as file:
    metadata = json.load(file)

RICE_CLASSES = metadata["class_names"]

rice_model = mobilenet_v2(weights=None)

rice_model.classifier[1] = nn.Linear(
    rice_model.last_channel,
    len(RICE_CLASSES)
)

checkpoint = torch.load(
    RICE_MODEL_PATH,
    map_location=device,
    weights_only=True
)

rice_model.load_state_dict(checkpoint["model_state_dict"])
rice_model = rice_model.to(device)
rice_model.eval()

rice_transform = transforms.Compose([
    transforms.Resize((224, 224)),
    transforms.ToTensor(),
    transforms.Normalize(
        mean=metadata["mean"],
        std=metadata["std"]
    )
])

print("Rice disease AI model loaded successfully!")


# --------------------------------
# 3. Rice disease prediction
# --------------------------------

def predict_rice_disease(image):

    image = image.convert("RGB")
    image_tensor = rice_transform(image).unsqueeze(0)

    with torch.no_grad():
        outputs = rice_model(image_tensor)
        probabilities = torch.softmax(outputs, dim=1)

        scores, indices = torch.topk(
            probabilities, k=3, dim=1
        )

    predictions = []

    for score, index in zip(
        scores[0].tolist(),
        indices[0].tolist()
    ):
        predictions.append({
            "label": RICE_CLASSES[index],
            "score": round(score, 4)
        })

    result = predictions[0]

    if result["score"] < 0.70:
        return {
            "status": "uncertain",
            "message": "The AI is not confident enough. Please upload another clear rice leaf image.",
            "confidence": round(result["score"] * 100, 2),
            "predictions": predictions
        }

    return {
        "status": "success",
        "disease": result["label"],
        "confidence": round(result["score"] * 100, 2),
        "predictions": predictions
    }


# --------------------------------
# 4. Existing plant disease prediction
# --------------------------------

def predict_disease(image, plant=None):

    if plant and plant.strip().lower() == "rice":
        return predict_rice_disease(image)

    predictions = classifier(image, top_k=3)

    result = predictions[0]

    if result["score"] < 0.70:
        return {
            "status": "uncertain",
            "message": "The AI is not confident enough. Please upload another clear leaf image.",
            "confidence": round(result["score"] * 100, 2),
            "predictions": predictions
        }

    return {
        "status": "success",
        "disease": result["label"],
        "confidence": round(result["score"] * 100, 2),
        "predictions": predictions
    }
