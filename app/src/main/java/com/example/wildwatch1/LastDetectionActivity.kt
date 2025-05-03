package com.example.wildwatch1

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
import com.google.firebase.database.*

class LastDetectionActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private lateinit var detectionText: TextView
    private lateinit var locationText: TextView
    private lateinit var databaseRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_last_detection)

        detectionText = findViewById(R.id.detectionText)
        locationText = findViewById(R.id.locationText)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // Firebase reference to the last detection node
        databaseRef = FirebaseDatabase.getInstance()
            .getReference("detections")

        findViewById<FloatingActionButton>(R.id.fabSimulate).setOnClickListener {
            simulateDetection()
        }
    }

    private fun loadLastDetection() {
        databaseRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val detectionType = snapshot.child("detectionType").getValue(String::class.java) ?: "Unknown"
                    val lat = snapshot.child("latitude").getValue(Double::class.java) ?: 0.0
                    val lng = snapshot.child("longitude").getValue(Double::class.java) ?: 0.0
                    val location = LatLng(lat, lng)

                    detectionText.text = "Last Detected Object: $detectionType"
                    locationText.text = "Location: (${lat.format(4)}, ${lng.format(4)})"

                    if (::map.isInitialized) {
                        map.clear()
                        map.addMarker(MarkerOptions().position(location).title("Detected: $detectionType"))
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15f))
                    }
                } else {
                    detectionText.text = "No detection data found."
                    locationText.text = ""
                }
            }

            override fun onCancelled(error: DatabaseError) {
                detectionText.text = "Failed to load detection."
                locationText.text = "Error: ${error.message}"
            }
        })
    }

    private fun simulateDetection() {
        // For testing: pushes dummy data to Firebase
        val testData = mapOf(
            "detectionType" to "Tiger",
            "latitude" to 33.7810324,
            "longitude" to 72.7221775
        )
        databaseRef.setValue(testData).addOnCompleteListener {
            loadLastDetection()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        loadLastDetection()
    }

    private fun Double.format(digits: Int) = "%.${digits}f".format(this)
}
