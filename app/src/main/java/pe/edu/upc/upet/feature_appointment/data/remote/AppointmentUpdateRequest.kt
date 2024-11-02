package pe.edu.upc.upet.feature_appointment.data.remote

data class AppointmentUpdateRequest(
    val diagnosis: String,
    val treatment: String
)