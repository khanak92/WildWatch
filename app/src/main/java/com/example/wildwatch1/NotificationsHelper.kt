package com.example.wildwatch1

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.AudioAttributes
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.net.toUri

object NotificationsHelper {

    @SuppressLint("MissingPermission")
    fun showNotification(
        context: Context,
        title: String,
        message: String,
        soundName: String, // e.g. "tiger", "lion", "cheetah"
        notificationId: Int
    ) {
        val channelId = "wildwatch_channel_$soundName"

        // Define the sound URI from raw resources
        val soundUri = "android.resource://${context.packageName}/raw/$soundName".toUri()

        // Create a notification channel (for Android 8.0+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val attributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build()

            val channel = NotificationChannel(
                channelId,
                "WildWatch Alerts ($soundName)",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Alert for $soundName detection"
                setSound(soundUri, attributes)
            }

            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        // Build the notification
        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.mipmap.ic_launcher) // Replace with your app icon
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setSound(soundUri)
            .setAutoCancel(true)

        // Show the notification
        with(NotificationManagerCompat.from(context)) {
            notify(notificationId, builder.build())
        }
    }
}
