
from fastapi import FastAPI, UploadFile, File, HTTPException
from PIL import Image, UnidentifiedImageError
from io import BytesIO

from model import predict_disease
from disease_info import get_disease_information

app = FastAPI(
    title="Smart Agriculture Disease Detection API",
    description="AI-based plant disease detection using Hugging Face",
    version="1.0"
)


@app.get("/")
def home():
    return {
        "message": "Smart Agriculture Disease Detection API is running"
    }


@app.post("/predict")
async def detect_disease(file: UploadFile = File(...)):

    if not file.content_type or not file.content_type.startswith("image/"):
        raise HTTPException(
            status_code=400,
            detail="Please upload a valid image file"
        )

    try:
        image_data = await file.read()
        image = Image.open(BytesIO(image_data)).convert("RGB")

        result = predict_disease(image)

        if result["status"] == "success":
            information = get_disease_information(result["disease"])
            result.update(information)

        return result

    except (UnidentifiedImageError, OSError):
        raise HTTPException(
            status_code=400,
            detail="Unable to read image. Please upload a valid image."
        )

    except Exception:
        raise HTTPException(
            status_code=500,
            detail="Disease prediction failed"
        )



@app.get("/disease-info/{disease_name:path}")
def disease_information(disease_name: str):
    return get_disease_information(disease_name)
