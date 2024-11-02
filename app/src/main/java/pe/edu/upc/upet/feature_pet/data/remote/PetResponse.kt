package pe.edu.upc.upet.feature_pet.data.remote

data class PetResponse(
    val id: Int,
    var name: String,
    val petOwnerId: Int,
    var breed: String,
    var species: String,
    var weight: Float,
    var birthdate: String,
    val image_url: String,
    val gender: GenderEnum
)

enum class GenderEnum {
    Male,
    Female
}
