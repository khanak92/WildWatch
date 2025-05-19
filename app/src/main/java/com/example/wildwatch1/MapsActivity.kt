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

    private lateinit var map: GoogleMap
    private lateinit var detectionTextView: TextView
    private val location = LatLng(33.7445, 72.7867)
    private val detectionType = "Camera's location is"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        detectionTextView = findViewById(R.id.detectionInfo)
        detectionTextView.text = "Showing: $detectionType at (${location.latitude.format(4)}, ${
            location.longitude.format(4)
        })"

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        map.clear()
        map.addMarker(MarkerOptions().position(location).title("Location: $detectionType"))
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15f))
    }

    private fun Double.format(digits: Int) = "%.${digits}f".format(this)
}
