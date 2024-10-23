package pe.edu.upc.upet.feature_review.data.remote

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface ReviewService {
    @POST("reviews/{petowner_id}")
    fun createReview(
        @Path("petowner_id") petOwnerId: Int,
        @Body reviewRequest: ReviewRequest
    ): Call<ReviewResponse>

}