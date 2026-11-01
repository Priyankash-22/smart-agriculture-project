
from fastapi import FastAPI, UploadFile, File, HTTPException, Form
from PIL import Image, UnidentifiedImageError
from io import BytesIO

from model import predict_disease
from disease_info import get_disease_information

app = FastAPI(
    title="Smart Agriculture Disease Detection API",
    description="AI-based plant disease detection using Hugging Face and custom Rice MobileNetV2",
    version="1.1"
)


@app.get("/")
def home():
    return {
        "message": "Smart Agriculture Disease Detection API is running",
        "models": [
            "Hugging Face Plant Disease Model",
            "Rice MobileNetV2 Model"
        ]
    }


@app.post("/predict")
async def detect_disease(
    file: UploadFile = File(...),
    plant: str = Form(None)
):

    if not file.content_type or not file.content_type.startswith("image/"):
        raise HTTPException(
            status_code=400,
            detail="Please upload a valid image file"
        )

    try:
        image_data = await file.read()
        image = Image.open(BytesIO(image_data)).convert("RGB")

        # Select the correct AI model
        result = predict_disease(image, plant)
        result["plant"] = plant if plant else "Unknown"

        # Add disease information for confident predictions
        if result["status"] == "success":
            information = get_disease_information(result["disease"])

            # Preserve the AI prediction status
            information.pop("status", None)

            result.update(information)

        return result

    except (UnidentifiedImageError, OSError):
        raise HTTPException(
            status_code=400,
            detail="Unable to read image. Please upload a valid image."
        )

    except Exception as error:
        print("Disease prediction error:", error)

        raise HTTPException(
            status_code=500,
            detail="Disease prediction failed"
        )


@app.get("/disease-info/{disease_name:path}")
def disease_information(disease_name: str):
    return get_disease_information(disease_name)
