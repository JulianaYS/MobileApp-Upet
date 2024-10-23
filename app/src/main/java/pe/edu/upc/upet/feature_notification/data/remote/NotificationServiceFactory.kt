package pe.edu.upc.upet.feature_notification.data.remote

import pe.edu.upc.upet.core_network.RetrofitFactory

class NotificationServiceFactory private constructor(){
    companion object {
        fun getNotificationService(): NotificationService {
            return RetrofitFactory.getRetrofit().create(NotificationService::class.java)
        }
    }
}