package com.example.wildwatch1

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton

class LastDetectionActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private lateinit var prefs: SharedPreferences

    private lateinit var detectionText: TextView
    private lateinit var locationText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_last_detection)

        detectionText = findViewById(R.id.detectionText)
        locationText = findViewById(R.id.locationText)

        prefs = getSharedPreferences("DetectionPrefs", Context.MODE_PRIVATE)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

        findViewById<FloatingActionButton>(R.id.fabSimulate).setOnClickListener {
            simulateDetection()
        }
    }

    private fun loadLastDetection() {
        NotificationUtils.loadDetectionData(this) { detectionData ->
            if (detectionData != null) {
                val detectionType = detectionData["detectionType"] as? String ?: "Unknown"
                val lat = (detectionData["latitude"] as? Double) ?: 0.0
                val lng = (detectionData["longitude"] as? Double) ?: 0.0
                val location = LatLng(lat, lng)

                detectionText.text = "Last Detected Object: $detectionType"
                locationText.text = "Location: (${lat.format(4)}, ${lng.format(4)})"

                if (::map.isInitialized) {
                    map.clear()
                    map.addMarker(MarkerOptions().position(location).title("Last Detection: $detectionType"))
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15f))
                }
            } else {
                detectionText.text = "No detection data available."
                locationText.text = ""
            }
        }
    }

    private fun simulateDetection() {
        prefs.edit().apply {
            putString("detection_type", "Tiger")
            putFloat("latitude", 37.7749f)
            putFloat("longitude", -122.4194f)
            apply()
        }
        loadLastDetection()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        loadLastDetection()
    }

    // Extension for formatting
    private fun Double.format(digits: Int) = "%.${digits}f".format(this)
}
