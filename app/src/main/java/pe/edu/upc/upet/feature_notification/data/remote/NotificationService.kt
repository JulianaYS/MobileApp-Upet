package pe.edu.upc.upet.feature_notification.data.remote

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface NotificationService {
    @POST("notifications")
    fun createNotification(@Body notificationRequest: NotificationRequest): Call<NotificationResponse>
}
