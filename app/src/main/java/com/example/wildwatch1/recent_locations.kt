package com.example.wildwatch1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class recent_locations : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recent_locations)


        val titleText = findViewById<TextView>(R.id.headerTitle)
        val lastDetectedBtn = findViewById<Button>(R.id.btnLastDetected)
        val mapsBtn = findViewById<Button>(R.id.btnMaps)



        lastDetectedBtn.setOnClickListener {
            startActivity(Intent(this, LastDetectionActivity::class.java))
        }

        mapsBtn.setOnClickListener {
            startActivity(Intent(this, MapsActivity::class.java))
        }

        titleText.text = "Recently Detected"
    }
}
