package com.example.wildwatch1

import android.Manifest
import android.util.Log
import androidx.annotation.RequiresPermission
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        Log.d("FCM", "Message received: ${message.notification?.title}")
        Log.d("FCM", "Body: ${message.notification?.body}")
        Log.d("FCM", "Data: ${message.data}")

        val title = message.notification?.title ?: "WildWatch Alert"
        val body = message.notification?.body ?: "Check for updates"
        val type = message.data["type"] ?: "default"

        // Save detection data if location info is present
        NotificationUtils.saveDetectionData(this, title, body)

        // Show notification
        NotificationUtils.showNotification(this, title, body, type)
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("FCM", "Refreshed token: $token")
        // Optionally save to Firestore or your server
    }
}
