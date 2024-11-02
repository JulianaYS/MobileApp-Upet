package pe.edu.upc.upet.feature_review.data.mapper

import pe.edu.upc.upet.feature_review.data.remote.ReviewResponse
import pe.edu.upc.upet.feature_review.domain.Review

fun ReviewResponse.toDomainModel(): Review {
    return Review(
        id = this.id,
        name = this.pet_owner_name,
        stars = this.stars,
        description = this.description,
        reviewTime = this.review_time,
        imageUrl = this.image_url
    )
}