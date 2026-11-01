from urllib.parse import unquote
DISEASE_INFORMATION = {

    "Apple___Apple_scab": {
        "plant": "Apple",
        "condition": "Apple Scab",
        "status": "disease",
        "description": "A fungal disease affecting apple leaves and fruits.",
        "symptoms": ["Olive-green or dark spots on leaves", "Scabby lesions on fruits", "Premature leaf fall"],
        "management": ["Remove fallen infected leaves.", "Prune to improve air circulation.", "Use locally recommended disease management practices."]
    },

    "Apple___Black_rot": {
        "plant": "Apple",
        "condition": "Apple Black Rot",
        "status": "disease",
        "description": "A fungal disease that affects apple fruits, leaves and branches.",
        "symptoms": ["Brown leaf spots", "Dark fruit rot", "Cankers on branches"],
        "management": ["Remove infected fruits and branches.", "Remove fallen plant debris.", "Consult an agricultural expert about suitable fungicides."]
    },

    "Apple___Cedar_apple_rust": {
        "plant": "Apple",
        "condition": "Cedar Apple Rust",
        "status": "disease",
        "description": "A fungal disease that causes damage to apple leaves and fruits.",
        "symptoms": ["Yellow-orange spots on leaves", "Orange fungal structures", "Leaf damage"],
        "management": ["Remove heavily infected plant material.", "Improve air circulation.", "Seek locally suitable disease management advice."]
    },

    "Apple___healthy": {
        "plant": "Apple",
        "condition": "Healthy",
        "status": "healthy",
        "description": "The image is classified as a healthy apple leaf.",
        "symptoms": [],
        "management": ["Continue regular plant monitoring.", "Maintain proper watering and nutrition."]
    },

    "Blueberry___healthy": {
        "plant": "Blueberry",
        "condition": "Healthy",
        "status": "healthy",
        "description": "The image is classified as a healthy blueberry leaf.",
        "symptoms": [],
        "management": ["Continue regular monitoring.", "Maintain suitable soil moisture and plant nutrition."]
    },

    "Cherry_(including_sour)___Powdery_mildew": {
        "plant": "Cherry",
        "condition": "Powdery Mildew",
        "status": "disease",
        "description": "A fungal disease affecting cherry leaves and young shoots.",
        "symptoms": ["White powdery coating", "Leaf curling", "Distorted young growth"],
        "management": ["Remove severely affected plant material.", "Improve air circulation.", "Follow locally recommended disease control practices."]
    },

    "Cherry_(including_sour)___healthy": {
        "plant": "Cherry",
        "condition": "Healthy",
        "status": "healthy",
        "description": "The image is classified as a healthy cherry leaf.",
        "symptoms": [],
        "management": ["Continue regular monitoring.", "Maintain proper plant care."]
    },

    "Corn_(maize)___Cercospora_leaf_spot Gray_leaf_spot": {
        "plant": "Corn",
        "condition": "Gray Leaf Spot",
        "status": "disease",
        "description": "A fungal disease that affects corn leaves.",
        "symptoms": ["Long rectangular gray or brown lesions", "Spots between leaf veins", "Reduced green leaf area"],
        "management": ["Remove or manage infected crop residues.", "Use resistant varieties where available.", "Follow local crop rotation recommendations."]
    },

    "Corn_(maize)___Common_rust_": {
        "plant": "Corn",
        "condition": "Common Rust",
        "status": "disease",
        "description": "A fungal disease that produces rust-colored pustules on corn leaves.",
        "symptoms": ["Small reddish-brown pustules", "Rust-like powder on leaves", "Yellowing around severe infections"],
        "management": ["Monitor crop regularly.", "Choose resistant varieties where available.", "Seek local agricultural advice for severe infection."]
    },

    "Corn_(maize)___Northern_Leaf_Blight": {
        "plant": "Corn",
        "condition": "Northern Leaf Blight",
        "status": "disease",
        "description": "A fungal disease that causes large lesions on corn leaves.",
        "symptoms": ["Long cigar-shaped gray-green lesions", "Brown leaf patches", "Premature drying of leaves"],
        "management": ["Use resistant varieties.", "Manage infected crop residues.", "Follow suitable crop rotation practices."]
    },

    "Corn_(maize)___healthy": {
        "plant": "Corn",
        "condition": "Healthy",
        "status": "healthy",
        "description": "The image is classified as a healthy corn leaf.",
        "symptoms": [],
        "management": ["Continue regular monitoring.", "Maintain appropriate irrigation and nutrition."]
    },

    "Grape___Black_rot": {
        "plant": "Grape",
        "condition": "Black Rot",
        "status": "disease",
        "description": "A fungal disease affecting grape leaves, stems and fruits.",
        "symptoms": ["Small brown circular spots", "Dark borders around leaf lesions", "Infected grapes may shrivel and become mummified"],
        "management": ["Remove infected leaves and mummified fruits.", "Clear infected plant debris.", "Prune vines to improve air circulation.", "Consult an agricultural expert for suitable fungicide recommendations."]
    },

    "Grape___Esca_(Black_Measles)": {
        "plant": "Grape",
        "condition": "Esca (Black Measles)",
        "status": "disease",
        "description": "A complex grapevine trunk disease that can affect leaves, wood and fruit.",
        "symptoms": ["Tiger-stripe patterns on leaves", "Leaf discoloration", "Dark spots on grapes"],
        "management": ["Remove severely affected plant material appropriately.", "Avoid injuries during pruning.", "Consult a grape disease specialist for management."]
    },

    "Grape___Leaf_blight_(Isariopsis_Leaf_Spot)": {
        "plant": "Grape",
        "condition": "Leaf Blight",
        "status": "disease",
        "description": "A fungal leaf disease that causes spots and damage to grape leaves.",
        "symptoms": ["Brown leaf spots", "Dark lesions", "Drying of affected leaf areas"],
        "management": ["Remove infected fallen leaves.", "Improve vine ventilation.", "Follow locally recommended disease control practices."]
    },

    "Grape___healthy": {
        "plant": "Grape",
        "condition": "Healthy",
        "status": "healthy",
        "description": "The image is classified as a healthy grape leaf.",
        "symptoms": [],
        "management": ["Continue regular monitoring.", "Maintain proper vine pruning and irrigation."]
    },

    "Orange___Haunglongbing_(Citrus_greening)": {
        "plant": "Orange",
        "condition": "Citrus Greening",
        "status": "disease",
        "description": "A serious bacterial disease of citrus plants spread by insect vectors.",
        "symptoms": ["Uneven yellowing of leaves", "Blotchy leaf patterns", "Small or misshapen fruits"],
        "management": ["Seek confirmation from a plant diagnostic laboratory.", "Monitor and manage insect vectors using local recommendations.", "Consult agricultural authorities about infected trees."]
    },

    "Peach___Bacterial_spot": {
        "plant": "Peach",
        "condition": "Bacterial Spot",
        "status": "disease",
        "description": "A bacterial disease affecting peach leaves and fruits.",
        "symptoms": ["Small dark leaf spots", "Yellow areas around spots", "Premature leaf drop"],
        "management": ["Use healthy planting material.", "Avoid unnecessary overhead irrigation.", "Follow locally recommended bacterial disease management."]
    },

    "Peach___healthy": {
        "plant": "Peach",
        "condition": "Healthy",
        "status": "healthy",
        "description": "The image is classified as a healthy peach leaf.",
        "symptoms": [],
        "management": ["Continue regular monitoring.", "Maintain suitable watering and nutrition."]
    },

    "Pepper,_bell___Bacterial_spot": {
        "plant": "Bell Pepper",
        "condition": "Bacterial Spot",
        "status": "disease",
        "description": "A bacterial disease affecting pepper leaves and fruits.",
        "symptoms": ["Small water-soaked spots", "Dark leaf lesions", "Yellowing and leaf drop"],
        "management": ["Use disease-free seeds and seedlings.", "Avoid working with wet plants.", "Remove infected plant debris and seek local advice."]
    },

    "Pepper,_bell___healthy": {
        "plant": "Bell Pepper",
        "condition": "Healthy",
        "status": "healthy",
        "description": "The image is classified as a healthy bell pepper leaf.",
        "symptoms": [],
        "management": ["Continue regular monitoring.", "Maintain proper irrigation and nutrition."]
    },

    "Potato___Early_blight": {
        "plant": "Potato",
        "condition": "Early Blight",
        "status": "disease",
        "description": "A fungal disease that commonly affects older potato leaves.",
        "symptoms": ["Brown circular spots", "Concentric ring patterns", "Yellowing around lesions"],
        "management": ["Remove infected plant debris.", "Use healthy seed potatoes.", "Avoid prolonged leaf wetness.", "Follow local disease management recommendations."]
    },

    "Potato___Late_blight": {
        "plant": "Potato",
        "condition": "Late Blight",
        "status": "disease",
        "description": "A destructive disease that can rapidly damage potato leaves, stems and tubers.",
        "symptoms": ["Water-soaked leaf patches", "Dark brown lesions", "White fungal growth under humid conditions"],
        "management": ["Remove and safely manage infected plant material.", "Avoid overhead irrigation where possible.", "Seek prompt local agricultural advice because the disease can spread rapidly."]
    },

    "Potato___healthy": {
        "plant": "Potato",
        "condition": "Healthy",
        "status": "healthy",
        "description": "The image is classified as a healthy potato leaf.",
        "symptoms": [],
        "management": ["Continue regular crop monitoring.", "Maintain suitable irrigation and nutrition."]
    },

    "Raspberry___healthy": {
        "plant": "Raspberry",
        "condition": "Healthy",
        "status": "healthy",
        "description": "The image is classified as a healthy raspberry leaf.",
        "symptoms": [],
        "management": ["Continue regular monitoring.", "Maintain suitable plant care."]
    },

    "Soybean___healthy": {
        "plant": "Soybean",
        "condition": "Healthy",
        "status": "healthy",
        "description": "The image is classified as a healthy soybean leaf.",
        "symptoms": [],
        "management": ["Continue regular monitoring.", "Maintain proper soil moisture and nutrition."]
    },

    "Squash___Powdery_mildew": {
        "plant": "Squash",
        "condition": "Powdery Mildew",
        "status": "disease",
        "description": "A fungal disease commonly affecting squash leaves.",
        "symptoms": ["White powdery patches", "Leaf yellowing", "Premature leaf drying"],
        "management": ["Remove severely affected leaves where practical.", "Improve air circulation.", "Follow locally recommended disease management."]
    },

    "Strawberry___Leaf_scorch": {
        "plant": "Strawberry",
        "condition": "Leaf Scorch",
        "status": "disease",
        "description": "A fungal disease causing damage to strawberry leaves.",
        "symptoms": ["Small dark purple spots", "Brown or scorched leaf edges", "Drying of affected leaves"],
        "management": ["Remove infected leaves.", "Avoid excessive leaf wetness.", "Maintain good crop hygiene."]
    },

    "Strawberry___healthy": {
        "plant": "Strawberry",
        "condition": "Healthy",
        "status": "healthy",
        "description": "The image is classified as a healthy strawberry leaf.",
        "symptoms": [],
        "management": ["Continue regular monitoring.", "Maintain appropriate watering and nutrition."]
    },

    "Tomato___Bacterial_spot": {
        "plant": "Tomato",
        "condition": "Bacterial Spot",
        "status": "disease",
        "description": "A bacterial disease affecting tomato leaves, stems and fruits.",
        "symptoms": ["Small dark leaf spots", "Yellowing around lesions", "Raised fruit spots"],
        "management": ["Use disease-free seeds and seedlings.", "Avoid handling wet plants.", "Remove infected plant debris."]
    },

    "Tomato___Early_blight": {
        "plant": "Tomato",
        "condition": "Early Blight",
        "status": "disease",
        "description": "A fungal disease that commonly affects older tomato leaves.",
        "symptoms": ["Brown spots with concentric rings", "Yellowing leaves", "Premature leaf fall"],
        "management": ["Remove infected lower leaves.", "Use mulch to reduce soil splash.", "Improve air circulation.", "Follow local disease management advice."]
    },

    "Tomato___Late_blight": {
        "plant": "Tomato",
        "condition": "Late Blight",
        "status": "disease",
        "description": "A serious disease that can rapidly damage tomato leaves, stems and fruits.",
        "symptoms": ["Water-soaked leaf patches", "Dark irregular lesions", "White growth under humid conditions"],
        "management": ["Remove infected plant material.", "Avoid overhead irrigation.", "Seek prompt agricultural advice."]
    },

    "Tomato___Leaf_Mold": {
        "plant": "Tomato",
        "condition": "Leaf Mold",
        "status": "disease",
        "description": "A fungal disease commonly associated with humid growing conditions.",
        "symptoms": ["Pale yellow patches on upper leaf surfaces", "Olive-green or gray mold underneath", "Leaf drying"],
        "management": ["Improve ventilation.", "Reduce excessive humidity.", "Avoid prolonged leaf wetness."]
    },

    "Tomato___Septoria_leaf_spot": {
        "plant": "Tomato",
        "condition": "Septoria Leaf Spot",
        "status": "disease",
        "description": "A fungal disease that causes numerous small spots on tomato leaves.",
        "symptoms": ["Small circular spots", "Gray centers with dark borders", "Yellowing and leaf drop"],
        "management": ["Remove infected lower leaves.", "Clear plant debris.", "Use mulch to reduce soil splash."]
    },

    "Tomato___Spider_mites Two-spotted_spider_mite": {
        "plant": "Tomato",
        "condition": "Two-Spotted Spider Mite",
        "status": "pest",
        "description": "A tiny mite pest that feeds on tomato leaves.",
        "symptoms": ["Tiny pale dots on leaves", "Yellow or bronze leaf appearance", "Fine webbing"],
        "management": ["Inspect the undersides of leaves.", "Monitor mite populations.", "Use locally recommended integrated pest management."]
    },

    "Tomato___Target_Spot": {
        "plant": "Tomato",
        "condition": "Target Spot",
        "status": "disease",
        "description": "A fungal disease that produces circular lesions on tomato leaves and other plant parts.",
        "symptoms": ["Brown circular spots", "Concentric rings", "Leaf yellowing"],
        "management": ["Remove infected plant debris.", "Improve air circulation.", "Avoid prolonged leaf wetness."]
    },

    "Tomato___Tomato_Yellow_Leaf_Curl_Virus": {
        "plant": "Tomato",
        "condition": "Tomato Yellow Leaf Curl Virus",
        "status": "disease",
        "description": "A viral disease spread by whiteflies that affects tomato growth.",
        "symptoms": ["Upward leaf curling", "Yellowing of leaves", "Stunted plant growth"],
        "management": ["Monitor and manage whitefly vectors.", "Remove severely infected plants according to local guidance.", "Use resistant varieties where available."]
    },

    "Tomato___Tomato_mosaic_virus": {
        "plant": "Tomato",
        "condition": "Tomato Mosaic Virus",
        "status": "disease",
        "description": "A viral disease affecting tomato leaves and plant growth.",
        "symptoms": ["Mosaic light and dark green patterns", "Leaf distortion", "Reduced plant growth"],
        "management": ["Remove infected plants according to local guidance.", "Disinfect tools.", "Use clean seeds and maintain field hygiene."]
    },

   
    
    "Tomato___healthy": {
        "plant": "Tomato",
        "condition": "Healthy",
        "status": "healthy",
        "description": "The image is classified as a healthy tomato leaf.",
        "symptoms": [],
        "management": ["Continue regular crop monitoring.", "Maintain proper irrigation and nutrition."]
    },

    # Rice disease information

    "Bacterial Leaf Blight": {
        "plant": "Rice",
        "condition": "Bacterial Leaf Blight",
        "status": "disease",
        "description": "A bacterial disease that affects rice leaves and can reduce crop growth and yield.",
        "symptoms": [
            "Yellowing along leaf edges",
            "Wavy, yellow to white lesions",
            "Drying of affected leaves"
        ],
        "management": [
            "Use healthy seeds and resistant rice varieties where available.",
            "Maintain proper field drainage.",
            "Avoid excessive nitrogen fertilizer.",
            "Follow local agricultural disease management advice."
        ]
    },

    "Brown Spot": {
        "plant": "Rice",
        "condition": "Brown Spot",
        "status": "disease",
        "description": "A fungal disease that causes brown spots on rice leaves.",
        "symptoms": [
            "Small circular or oval brown spots",
            "Yellow halos around some spots",
            "Dark lesions on leaves"
        ],
        "management": [
            "Use healthy seeds.",
            "Maintain balanced soil nutrition.",
            "Remove heavily infected plant residues.",
            "Follow locally recommended disease management practices."
        ]
    },

    "Healthy Rice Leaf": {
        "plant": "Rice",
        "condition": "Healthy",
        "status": "healthy",
        "description": "The image is classified as a healthy rice leaf.",
        "symptoms": [],
        "management": [
            "Continue regular crop monitoring.",
            "Maintain proper irrigation and nutrition."
        ]
    },

    "Leaf Blast": {
        "plant": "Rice",
        "condition": "Leaf Blast",
        "status": "disease",
        "description": "A fungal disease that affects rice leaves and may cause serious crop damage.",
        "symptoms": [
            "Spindle-shaped leaf lesions",
            "Gray or whitish centers with dark borders",
            "Drying of affected leaves"
        ],
        "management": [
            "Grow resistant varieties where available.",
            "Avoid excessive nitrogen application.",
            "Maintain suitable field water management.",
            "Consult an agricultural expert for local treatment recommendations."
        ]
    },

    "Leaf scald": {
        "plant": "Rice",
        "condition": "Leaf Scald",
        "status": "disease",
        "description": "A fungal disease that causes scald-like lesions on rice leaves.",
        "symptoms": [
            "Brown or gray lesions near leaf tips",
            "Scalded or burned-looking leaf areas",
            "Drying of leaf edges"
        ],
        "management": [
            "Use healthy seeds and suitable varieties.",
            "Remove infected crop residues where practical.",
            "Maintain balanced fertilizer application.",
            "Follow local agricultural recommendations."
        ]
    },

    "Narrow Brown Leaf Spot": {
        "plant": "Rice",
        "condition": "Narrow Brown Leaf Spot",
        "status": "disease",
        "description": "A fungal disease that produces narrow brown lesions on rice leaves.",
        "symptoms": [
            "Narrow, elongated brown spots",
            "Brown streaks along leaf veins",
            "Drying of affected leaf areas"
        ],
        "management": [
            "Maintain balanced soil nutrition.",
            "Use healthy seeds.",
            "Manage infected crop residues.",
            "Seek local agricultural advice for severe infections."
        ]
    },

    "Rice Hispa": {
        "plant": "Rice",
        "condition": "Rice Hispa",
        "status": "pest",
        "description": "An insect pest whose adults and grubs damage rice leaves.",
        "symptoms": [
            "White parallel feeding streaks on leaves",
            "Scraped leaf surfaces",
            "Whitish or damaged patches on leaves"
        ],
        "management": [
            "Monitor the crop regularly for adult beetles and damaged leaves.",
            "Remove heavily infested leaves where practical.",
            "Maintain field sanitation.",
            "Use locally recommended integrated pest management practices."
        ]
    },

    "Sheath Blight": {
        "plant": "Rice",
        "condition": "Sheath Blight",
        "status": "disease",
        "description": "A fungal disease that affects rice leaf sheaths and can spread to leaves.",
        "symptoms": [
            "Oval or irregular greenish-gray lesions on leaf sheaths",
            "Lesions with brown margins",
            "Drying of affected leaves"
        ],
        "management": [
            "Avoid excessive nitrogen fertilizer.",
            "Maintain suitable plant spacing.",
            "Manage infected crop residues.",
            "Follow local agricultural disease management advice."
        ]
    }
}

def get_disease_information(disease_name):

    disease_name = unquote(disease_name)

    return DISEASE_INFORMATION.get(
        disease_name,
        {
            "plant": "Unknown",
            "condition": disease_name,
            "status": "unknown",
            "description": "Disease information is not available.",
            "symptoms": [],
            "management": [
                "Consult a local agricultural expert for confirmation."
            ]
        }
    )


