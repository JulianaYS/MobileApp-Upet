package pe.edu.upc.upet.feature_vetClinic.domain

typealias Veterinaries = List<VeterinaryClinic>
data class VeterinaryClinic(
    val id: Int,
    val name: String,
    val location : String,
    val services: String,
    val image_url: String,
    val description: String,
    val office_hours_start : String,
    val office_hours_end : String,
)