package pe.edu.upc.upet.feature_pet.data.remote

data class PetRequest(
    val name: String,
    val breed: String,
    val species: String,
    val weight: Float,
    val birthdate: String,
    val image_url: String,
    val gender: String
)