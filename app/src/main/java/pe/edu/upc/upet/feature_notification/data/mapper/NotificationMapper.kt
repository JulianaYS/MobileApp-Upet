package pe.edu.upc.upet.feature_notification.data.mapper

import pe.edu.upc.upet.feature_notification.data.remote.NotificationResponse
import pe.edu.upc.upet.feature_notification.domain.Notification

fun NotificationResponse.toDomain(): Notification {
    return Notification(
        id = id,
        userId = userId,
        type = type,
        message = message,
        datetime = datetime
    )
}