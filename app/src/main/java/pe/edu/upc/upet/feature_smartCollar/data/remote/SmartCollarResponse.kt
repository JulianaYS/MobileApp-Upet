package pe.edu.upc.upet.feature_smartCollar.data.remote

import pe.edu.upc.upet.feature_smartCollar.domain.Location

data class SmartCollarResponse (
    val id: Int,
    val serial_number: String,
    val temperature: Double,
    val lpm: Int,
    val battery: Double,
    val location: Location,
    val petId: Int?
)