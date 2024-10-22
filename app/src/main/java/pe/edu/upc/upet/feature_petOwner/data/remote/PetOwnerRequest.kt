package pe.edu.upc.upet.feature_petOwner.data.remote

data class PetOwnerRequest (
    val numberPhone: String,
    val location: String
)

data class EditPetOwnerRequest (
    val name: String,
    val numberPhone: String,
    val location: String
)