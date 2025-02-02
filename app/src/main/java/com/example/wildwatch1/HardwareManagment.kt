@file:Suppress("DEPRECATION")

package com.example.wildwatch1

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.Date

class HardwareManagment : AppCompatActivity() {
    private lateinit var edtSerialNumber: EditText
    private lateinit var edtCameraName: EditText
    private lateinit var edtIpAddress: EditText
    private lateinit var edtCameraLocation: EditText
    private lateinit var btnAddCamera: Button
    private lateinit var btnRemoveCamera: Button
    private lateinit var playerView: StyledPlayerView
    private var player: ExoPlayer? = null

    private lateinit var database: DatabaseReference // Firebase Database Reference

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hardware_managment)

        // Initialize Firebase Database Reference
        database = FirebaseDatabase.getInstance().getReference("Cameras")

        // Initialize UI Elements
        edtSerialNumber = findViewById(R.id.edtSerialNumber)
        edtCameraName = findViewById(R.id.edtCameraName)
        edtIpAddress = findViewById(R.id.edtIpAddress)
        edtCameraLocation = findViewById(R.id.edtCameraLocation)
        btnAddCamera = findViewById(R.id.btnAddCamera)
        btnRemoveCamera = findViewById(R.id.btnRemoveCamera)
        playerView = findViewById(R.id.playerView)

        // Handle Add Camera Button Click
        btnAddCamera.setOnClickListener {
            addCamera()
        }

        // Handle Remove Camera Button Click
        btnRemoveCamera.setOnClickListener {
            removeCamera()
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun addCamera() {
        val serialNumber = edtSerialNumber.text.toString().trim()
        val cameraName = edtCameraName.text.toString().trim()
        val ipAddress = edtIpAddress.text.toString().trim()
        val location = edtCameraLocation.text.toString().trim()

        if (serialNumber.isEmpty() || cameraName.isEmpty() || ipAddress.isEmpty() || location.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }

        // Get Current Date and Time
        val timestamp = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date())

        // Create a Camera Object
        val cameraData = Camera(serialNumber, cameraName, ipAddress, location, timestamp)

        // Store Data in Firebase
        database.child(serialNumber).setValue(cameraData).addOnSuccessListener {
            Toast.makeText(this, "Camera Added Successfully!", Toast.LENGTH_SHORT).show()
            startRTSPStream(ipAddress) // Start RTSP Stream
        }.addOnFailureListener {
            Toast.makeText(this, "Failed to Add Camera", Toast.LENGTH_SHORT).show()
        }
    }

    private fun removeCamera() {
        val serialNumber = edtSerialNumber.text.toString().trim()

        if (serialNumber.isEmpty()) {
            Toast.makeText(this, "Enter Serial Number to Remove Camera", Toast.LENGTH_SHORT).show()
            return
        }

        database.child(serialNumber).removeValue().addOnSuccessListener {
            Toast.makeText(this, "Camera Removed Successfully!", Toast.LENGTH_SHORT).show()
            stopRTSPStream() // Stop the camera stream
        }.addOnFailureListener {
            Toast.makeText(this, "Failed to Remove Camera", Toast.LENGTH_SHORT).show()
        }
    }

    private fun startRTSPStream(rtspUrl: String) {
        stopRTSPStream() // Stop any existing stream before starting a new one

        player = ExoPlayer.Builder(this).build().apply {
            setMediaItem(MediaItem.Builder().setUri(rtspUrl).build()) // Use the latest MediaItem format
            prepare()
            playWhenReady = true
        }

        playerView.player = player
    }

    private fun stopRTSPStream() {
        player?.release()
        player = null
    }

    override fun onDestroy() {
        super.onDestroy()
        stopRTSPStream()
    }
}

// Data Class for Storing Camera Info in Firebase
data class Camera(
    val serialNumber: String,
    val cameraName: String,
    val ipAddress: String,
    val location: String,
    val timestamp: String
)
