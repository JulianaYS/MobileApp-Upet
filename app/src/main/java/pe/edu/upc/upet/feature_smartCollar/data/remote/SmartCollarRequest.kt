package pe.edu.upc.upet.feature_smartCollar.data.remote

import pe.edu.upc.upet.feature_smartCollar.domain.Location

data class SmartCollarRequest(
    val serialNumber: String,
    val temperature: Double,
    val lpm: Int,
    val battery: Double,
    val location: Location
)