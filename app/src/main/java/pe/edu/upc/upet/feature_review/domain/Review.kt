package pe.edu.upc.upet.feature_review.domain

data class Review (
    val id: Int,
    val name: String,
    val stars: Int,
    val description: String,
    val reviewTime: String,
    val imageUrl: String,
    )