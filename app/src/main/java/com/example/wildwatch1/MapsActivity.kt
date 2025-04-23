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

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private var lastDetection: String? = null
    private var location: LatLng? = null
    private lateinit var map: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        NotificationUtils.loadDetectionData(this) { detectionData ->
            if (detectionData != null) {
                lastDetection = detectionData["detectionType"] as String
                val lat = detectionData["latitude"] as Double
                val lng = detectionData["longitude"] as Double
                location = LatLng(lat, lng)

                // Initialize the map now that we have location data
                val mapFragment = supportFragmentManager
                    .findFragmentById(R.id.map) as SupportMapFragment
                mapFragment.getMapAsync(this)

                findViewById<TextView>(R.id.detectionInfo).text =
                    "Detected: $lastDetection at ($lat, $lng)"
            } else {
                findViewById<TextView>(R.id.detectionInfo).text =
                    "No detection data available."
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        location?.let {
            val marker = MarkerOptions()
                .position(it)
                .title("Detected Location")
            map.addMarker(marker)
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(it, 15f))
        }
    }
}
