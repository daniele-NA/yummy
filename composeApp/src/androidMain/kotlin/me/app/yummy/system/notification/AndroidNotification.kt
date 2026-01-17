package me.app.yummy.system.notification

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.annotation.MainThread
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import me.app.yummy.MainActivity
import me.app.yummy.R
import me.app.yummy.core.result.ResultState
import me.app.yummy.core.values.AndroidValues.NOTIFICATION_CHANNEL_DESC
import me.app.yummy.core.values.AndroidValues.NOTIFICATION_CHANNEL_ID
import me.app.yummy.core.values.AndroidValues.NOTIFICATION_CHANNEL_NAME


private fun createNotificationChannel(context: Context) {
    val channel = NotificationChannel(
        NOTIFICATION_CHANNEL_ID,
        NOTIFICATION_CHANNEL_NAME,
        NotificationManager.IMPORTANCE_HIGH
    ).apply {
        enableVibration(true)
        description = NOTIFICATION_CHANNEL_DESC
    }
    val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    manager.createNotificationChannel(channel)
}

fun buildNotification(
    context: Context,
    title: String, body: String
): ResultState<Notification> {
    return try {
        createNotificationChannel(context)

        // == PENDING INTENT TO OPEN MainActivity == //
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(
            context, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val builder = NotificationCompat.Builder(
            context,
            NOTIFICATION_CHANNEL_ID
        )
            .setSmallIcon(R.drawable.main_ic)
            .setContentTitle(title)
            .setContentText(body)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000))
            .setContentIntent(pendingIntent)
            .setStyle(
                NotificationCompat.BigTextStyle().bigText(body)
            ) // Abbellimento per messaggi lunghi

        val notification = builder.build()

        ResultState.Success(notification)
    } catch (e: Exception) {
        ResultState.Error(e)
    }
}


@MainThread
@RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
fun showNotification(context: Context,notification: Notification){
    NotificationManagerCompat.from(context).notify(0, notification)
}

