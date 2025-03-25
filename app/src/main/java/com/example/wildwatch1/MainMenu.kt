package com.example.wildwatch1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainMenu : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

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
            startActivity(Intent(this, precautionary_measures::class.java))
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
