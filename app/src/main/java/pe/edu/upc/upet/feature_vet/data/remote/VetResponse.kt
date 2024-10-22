package pe.edu.upc.upet.feature_vet.data.remote

import com.google.gson.annotations.SerializedName

typealias  VetResponseList = List<VetResponse>
data class VetResponse(
    val id: Int,
    val name: String,
    val clinicId: Int,
    @SerializedName("image_url")
    val imageUrl: String,
    val description: String?,
    val experience: Int?,
    @SerializedName("user_id")
    val userId: Int
)