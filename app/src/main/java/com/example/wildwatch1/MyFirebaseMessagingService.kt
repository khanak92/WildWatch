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

        val title = message.notification?.title ?: "WildWatch Alert"
        val body = message.notification?.body ?: "Check for updates"
        val type = message.data["type"] ?: "default"

        Log.d("FCM", "Title: $title")
        Log.d("FCM", "Body: $body")
        Log.d("FCM", "Type: $type")
        Log.d("FCM", "Data Payload: ${message.data}")

        val latStr = message.data["latitude"]
        val lngStr = message.data["longitude"]

        if (!latStr.isNullOrEmpty() && !lngStr.isNullOrEmpty()) {
            val latitude = latStr.toDoubleOrNull()
            val longitude = lngStr.toDoubleOrNull()

            if (latitude != null && longitude != null) {
                // 🗂️ Save detection with location
                NotificationUtils.saveDetectionData(this, title, latitude.toString(), longitude.toString())
                Log.d("FCM", "Detection data saved: $title at ($latitude, $longitude)")
            } else {
                Log.e("FCM", "Invalid coordinates: latitude=$latStr, longitude=$lngStr")
            }
        } else {
            Log.w("FCM", "No location data in message")
        }

        // 🔔 Show alert notification if not muted
        NotificationUtils.showNotification(this, title, body, type)
    }





    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("FCM", "Refreshed token: $token")
        // Optionally save to Firestore or your server
    }
}
