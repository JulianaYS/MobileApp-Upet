package pe.edu.upc.upet.feature_pet.domain

import pe.edu.upc.upet.feature_pet.data.remote.GenderEnum

data class Pet(
    val id: Int,
    val name: String,
    val petOwnerId: Int,
    val birthdate: String,
    val breed: String,
    val image_url: String,
    val weight: Float,
    val specie: String,
    val gender: GenderEnum
)
