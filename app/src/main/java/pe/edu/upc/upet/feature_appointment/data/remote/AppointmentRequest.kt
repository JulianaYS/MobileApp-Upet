package pe.edu.upc.upet.feature_appointment.data.remote

import com.google.gson.annotations.SerializedName

data class AppointmentRequest(
    @SerializedName("date_day")
    val dateDay: String,
    val description: String,
    @SerializedName("start_time")
    val startTime: String,
    @SerializedName("pet_id")
    val petId: Int,
    @SerializedName("veterinarian_id")
    val veterinarianId: Int
)
