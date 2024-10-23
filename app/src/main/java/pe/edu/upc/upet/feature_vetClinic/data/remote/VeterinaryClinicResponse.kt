package pe.edu.upc.upet.feature_vetClinic.data.remote

typealias  VeterinaryClinicResponseList = List<VeterinaryClinicResponse>
data class VeterinaryClinicResponse(
    val id: Int,
    val name: String,
    val location : String,
    val services: String,
    val image_url: String,
    val description: String,
    val office_hours_start : String,
    val office_hours_end : String,
)