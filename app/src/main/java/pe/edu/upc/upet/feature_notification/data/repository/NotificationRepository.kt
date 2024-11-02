package pe.edu.upc.upet.feature_notification.data.repository

import pe.edu.upc.upet.feature_notification.data.remote.NotificationRequest
import pe.edu.upc.upet.feature_notification.data.remote.NotificationResponse
import pe.edu.upc.upet.feature_notification.data.remote.NotificationService
import pe.edu.upc.upet.feature_notification.data.remote.NotificationServiceFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotificationRepository(
    private val notificationService: NotificationService = NotificationServiceFactory.getNotificationService()
){
    fun createNotification(notification: NotificationRequest, callback: (Boolean)->Unit){
        notificationService.createNotification(notification).enqueue(object :
            Callback<NotificationResponse> {
            override fun onResponse(call: Call<NotificationResponse>, response: Response<NotificationResponse>) {
                if (response.isSuccessful) {
                    callback(true)
                } else {
                    callback(false)
                }
            }

            override fun onFailure(call: Call<NotificationResponse>, t: Throwable) {
                callback(false)
            }
        })
    }
}