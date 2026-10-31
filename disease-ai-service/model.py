
from transformers import pipeline

MODEL_NAME = "neghani/plantvillage-mobilenetv2"

print("Loading plant disease AI model...")

classifier = pipeline(
    "image-classification",
    model=MODEL_NAME,
    device=-1
)

print("Plant disease AI model loaded successfully!")


def predict_disease(image):
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
