package com.example.wildwatch1

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

object NotificationUtils {

    private const val CHANNEL_ID = "wildwatch_alerts"
    private const val PREFS_NAME = "WildWatchPrefs"
    private const val MUTE_KEY = "isMuted"

    fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "WildWatch Alerts",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Important Wildlife Alerts"
                enableLights(true)
                lightColor = Color.RED
            }
            val manager = context.getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }


    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    fun showNotification(context: Context, title: String, body: String, type: String) {
        if (getMuteStatus(context)) return

        val intent = Intent(context, NotificationsActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("type", type)
        }

        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.edittextshape) // Update with actual icon
            .setContentTitle(title)
            .setContentText(body)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        NotificationManagerCompat.from(context).notify(1001, builder.build())
    }


    fun saveDetectionData(
        context: Context,
        detectionType: String,
        latitude: String,
        longitude: String
    ) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().apply {
            putString("last_detection", detectionType)
            putFloat("latitude", latitude.toFloat())
            putFloat("longitude", longitude.toFloat())
            apply()
        }
    }

    fun loadDetectionData(context: Context, callback: (Map<String, Any>?) -> Unit) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val type = prefs.getString("last_detection", null)
        val lat = prefs.getFloat("latitude", -1f)
        val lng = prefs.getFloat("longitude", -1f)

        if (type != null && lat != -1f && lng != -1f) {
            callback(
                mapOf(
                    "detectionType" to type,
                    "latitude" to lat.toDouble(),
                    "longitude" to lng.toDouble()
                )
            )
        } else {
            callback(null)
        }
    }


    fun getMuteStatus(context: Context): Boolean {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getBoolean(MUTE_KEY, false)
    }


    fun toggleMute(context: Context) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val current = prefs.getBoolean(MUTE_KEY, false)
        prefs.edit().putBoolean(MUTE_KEY, !current).apply()
    }
}
