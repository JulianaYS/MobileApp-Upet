package pe.edu.upc.upet.feature_smartCollar.domain

import pe.edu.upc.upet.feature_pet.data.remote.GenderEnum

data class SmartCollar (
    val id: Int,
    val serial_number: String,
    val temperature: Double,
    val lpm: Int,
    val battery: Double,
    val location: Location,
    val pet_id: Int?
)

data class Location(
    val latitude: Double,
    val longitude: Double
)