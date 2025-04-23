package com.example.wildwatch1

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import android.widget.TextView

class LastDetectionActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private var lastDetection: String? = null
    private var location: LatLng? = null
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

        loadLastDetection()
    }

    private fun loadLastDetection() {
        lastDetection = prefs.getString("detection_type", null)
        val lat = prefs.getFloat("latitude", 0f).toDouble()
        val lng = prefs.getFloat("longitude", 0f).toDouble()

        if (lastDetection != null && (lat != 0.0 || lng != 0.0)) {
            location = LatLng(lat, lng)
            detectionText.text = "Last Detected Object: $lastDetection"
            locationText.text = "Location: $location"

            if (::map.isInitialized) {
                map.clear()
                map.addMarker(MarkerOptions().position(location!!).title("Last Detection"))
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(location!!, 15f))
            }
        } else {
            detectionText.text = "No detection data available."
            locationText.text = ""
        }
    }

    private fun simulateDetection() {
        val editor = prefs.edit()
        editor.putString("detection_type", "Tiger")
        editor.putFloat("latitude", 37.7749f)
        editor.putFloat("longitude", -122.4194f)
        editor.apply()

        loadLastDetection()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        loadLastDetection()
    }
}
