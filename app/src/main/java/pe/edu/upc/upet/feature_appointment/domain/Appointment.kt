package pe.edu.upc.upet.feature_appointment.domain

data class Appointment(
    val id: Int,
    val day: String,
    val diagnosis: String?,
    val treatment: String?,
    val description: String,
    val petId: Int,
    val veterinarianId: Int,
    val startTime: String,
    val endTime: String,
    val status: String
) {
}