package pe.edu.upc.upet.feature_review.data.remote

import pe.edu.upc.upet.core_network.RetrofitFactory

class ReviewServiceFactory {
    companion object {
        fun getReviewService(): ReviewService {
            return RetrofitFactory.getRetrofit().create(ReviewService::class.java)
        }
    }
}