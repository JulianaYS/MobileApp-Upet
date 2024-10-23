package pe.edu.upc.upet.feature_review.data.remote

data class ReviewResponse (
    val id: Int,
    val pet_owner_name: String,
    val stars: Int,
    val description: String,
    val review_time: String,
    val image_url: String
)
data class VetResponseWithReviews(
    val id: Int,
    val name: String,
    val image_url: String,
    val description: String?,
    val experience: Int?,
    val clinicName: String,
    val workingHourStart: String,
    val workingHourEnd: String,
    val clinicAddress: String,
    val reviews: List<ReviewResponse>
)