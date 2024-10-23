package pe.edu.upc.upet.ui.screens.shared.notification

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.core.app.NotificationCompat
import pe.edu.upc.upet.R

class ScheduledNotification: BroadcastReceiver() {
    companion object{
        const val NOTIFICATION_ID = 10
    }
    override fun onReceive(context: Context, intent: Intent) {
        val type = intent.getStringExtra("type")
        val message = intent.getStringExtra("message")
        createNotification(context, type, message)
    }
    private fun createNotification(context: Context, type: String?, message: String?){
        val bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.upet)
        val notification = NotificationCompat.Builder(context, "Canal")
            .setSmallIcon(R.mipmap.group)
            .setLargeIcon(bitmap)
            .setContentTitle("\uD83D\uDC3E $type")
            .setContentText(message)
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(NOTIFICATION_ID, notification)

    }
}