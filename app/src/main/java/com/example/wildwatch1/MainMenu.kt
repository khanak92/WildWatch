package com.example.wildwatch1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.messaging.FirebaseMessaging
import android.util.Log
import android.widget.Toast

class MainMenu : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("FCM", "Fetching FCM registration token failed", task.exception)
                return@addOnCompleteListener
            }

            // ✅ Successfully got the token
            val token = task.result
            Log.d("FCM_TOKEN", "Your device token is: $token")

            // Optional: Show as a Toast
            //Toast.makeText(this, "Token: $token", Toast.LENGTH_LONG).show()
        }
        NotificationUtils.createNotificationChannel(this)


        // Find buttons by their IDs
        val btnPrecautionaryMeasures = findViewById<Button>(R.id.btnPrecautionaryMeasures)
        val btnRecentlyDetected = findViewById<Button>(R.id.btnRecentlyDetected)
        val btnHardwareManagement = findViewById<Button>(R.id.btnHardwareManagement)
        val btnTrendAnalysis = findViewById<Button>(R.id.btnTrendAnalysis)
        val btnUserProfile = findViewById<Button>(R.id.btnUserProfile)
        val btnLiveStream = findViewById<Button>(R.id.btnLiveStream)
        val btnNotificationSettings = findViewById<Button>(R.id.btnNotificationSettings)
        val btnContactWildlifeDepartment = findViewById<Button>(R.id.btnContactWildlifeDepartment)

        // Set up click listeners for each button
        btnPrecautionaryMeasures.setOnClickListener {
            startActivity(Intent(this, precautionaryMeasures2::class.java))
        }

        btnRecentlyDetected.setOnClickListener {
            startActivity(Intent(this, recent_locations::class.java))
        }

        btnHardwareManagement.setOnClickListener {
            startActivity(Intent(this, HardwareManagement::class.java))
        }

        btnTrendAnalysis.setOnClickListener {
            startActivity(Intent(this, trendAnalysis::class.java))
        }

        btnUserProfile.setOnClickListener {
            startActivity(Intent(this, userProfile::class.java))
        }

        btnLiveStream.setOnClickListener {
            startActivity(Intent(this, liveStream::class.java))
        }

        btnNotificationSettings.setOnClickListener {
            startActivity(Intent(this, notificationMgt::class.java))
        }

        btnContactWildlifeDepartment.setOnClickListener {
            // Example: Navigate to a contact screen or trigger a function
            startActivity(Intent(this, precautionary_measures::class.java))
        }
    }
}
