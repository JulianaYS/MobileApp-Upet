package pe.edu.upc.upet.feature_notification.data.remote

import java.time.LocalDateTime

data class NotificationResponse(
    val id: Int,
    val userId: Int,
    val type: String,
    val message: String,
    val datetime: LocalDateTime
)
