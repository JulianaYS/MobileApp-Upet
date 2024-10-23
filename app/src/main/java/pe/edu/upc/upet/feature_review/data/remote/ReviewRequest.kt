package pe.edu.upc.upet.feature_review.data.remote

import com.google.gson.annotations.SerializedName

class ReviewRequest (
    val stars: Int,
    val description: String,
    @SerializedName("veterinarian_id")
    val veterinarianId: Int
)