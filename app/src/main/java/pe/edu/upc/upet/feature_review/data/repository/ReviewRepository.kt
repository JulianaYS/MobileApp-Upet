package pe.edu.upc.upet.feature_review.data.repository

import android.util.Log
import pe.edu.upc.upet.feature_review.data.remote.ReviewRequest
import pe.edu.upc.upet.feature_review.data.remote.ReviewResponse
import pe.edu.upc.upet.feature_review.data.remote.ReviewService
import pe.edu.upc.upet.feature_review.data.remote.ReviewServiceFactory

class ReviewRepository(
    private val reviewService: ReviewService = ReviewServiceFactory.getReviewService()
) {
    fun createReview(petownerId: Int, reviewRequest: ReviewRequest, onSuccess: (ReviewResponse) -> Unit, onError: (String) -> Unit){
        reviewService.createReview(petownerId, reviewRequest).enqueue(object : retrofit2.Callback<ReviewResponse> {
            override fun onResponse(call: retrofit2.Call<ReviewResponse>, response: retrofit2.Response<ReviewResponse>) {
                if (response.isSuccessful) {
                    val reviewResponse = response.body()?.let { reviewResponse ->
                        ReviewResponse(
                            id = reviewResponse.id,
                            pet_owner_name = reviewResponse.pet_owner_name,
                            stars = reviewResponse.stars,
                            description = reviewResponse.description,
                            review_time = reviewResponse.review_time,
                            image_url = reviewResponse.image_url
                        )
                    }
                    onSuccess(reviewResponse!!)
                } else {
                    Log.e("ReviewRepository", "Error creating review, response unsuccessful. Response: $response")
                    onError("Error")
                }
            }
            override fun onFailure(call: retrofit2.Call<ReviewResponse>, t: Throwable) {
                Log.e("ReviewRepository", "Error creating review, request failed. Throwable: $t")
                onError(t.message ?: "Error")
            }
        })
    }
}