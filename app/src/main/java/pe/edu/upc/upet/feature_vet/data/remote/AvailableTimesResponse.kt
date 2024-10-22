package pe.edu.upc.upet.feature_vet.data.remote

import com.google.gson.annotations.SerializedName

data class AvailableTimesResponse (
    val date: String,
    @SerializedName("available_times")
    val availableTimes: List<String>
)