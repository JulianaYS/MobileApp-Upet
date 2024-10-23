package pe.edu.upc.upet.feature_appointment.data.remote

import com.google.gson.annotations.SerializedName

data class AppointmentResponse(
    val id: Int,
    @SerializedName("date_day")
    val dateDay: String,
    val diagnosis: String,
    val treatment: String,
    val description: String,
    @SerializedName("start_time")
    val startTime: String,
    @SerializedName("end_time")
    val endTime: String,
    @SerializedName("pet_id")
    val petId: Int,
    @SerializedName("veterinarian_id")
    val veterinarianId: Int,
    val status: String
)