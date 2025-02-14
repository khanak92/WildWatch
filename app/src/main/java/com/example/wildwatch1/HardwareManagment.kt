@file:Suppress("DEPRECATION")

package com.example.wildwatch1


import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.AudioManager
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import org.opencv.android.OpenCVLoader
import org.opencv.android.CameraBridgeViewBase
import org.opencv.core.Mat
import org.pytorch.Tensor
import java.nio.ByteBuffer
import java.text.SimpleDateFormat
import java.util.*

class HardwareManagment : AppCompatActivity(), CameraBridgeViewBase.CvCameraViewListener2 {

    private lateinit var edtSerialNumber: EditText
    private lateinit var edtCameraName: EditText
    private lateinit var edtIpAddress: EditText
    private lateinit var edtCameraLocation: EditText
    private lateinit var btnAddCamera: Button
    private lateinit var btnRemoveCamera: Button
    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hardware_managment)

        // Ensure OpenCV is Loaded
        if (!OpenCVLoader.initDebug()) {
            Toast.makeText(this, "OpenCV initialization failed!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "OpenCV loaded successfully!", Toast.LENGTH_SHORT).show()
        }

        // Initialize Firebase
        database = FirebaseDatabase.getInstance()

        // Initialize UI Elements
        edtSerialNumber = findViewById(R.id.edtSerialNumber)
        edtCameraName = findViewById(R.id.edtCameraName)
        edtIpAddress = findViewById(R.id.edtIpAddress)
        edtCameraLocation = findViewById(R.id.edtCameraLocation)
        btnAddCamera = findViewById(R.id.btnAddCamera)
        btnRemoveCamera = findViewById(R.id.btnRemoveCamera)

        // Handle Add Camera Button Click
        btnAddCamera.setOnClickListener {
            addCamera()
        }

        // Handle Remove Camera Button Click
        btnRemoveCamera.setOnClickListener {
            removeCamera()
        }
    }

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
        val timestamp = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())

        // Create a Camera Object
        val cameraData = dataClass.Camera(serialNumber, cameraName, ipAddress, location, timestamp)

        // Store Data in Firebase
        database.reference.child("Cameras").child(serialNumber).setValue(cameraData)
            .addOnSuccessListener {
                Toast.makeText(this, "Camera Added Successfully!", Toast.LENGTH_SHORT).show()
                startProcessing(ipAddress) // Start RTSP Stream & Processing
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to Add Camera", Toast.LENGTH_SHORT).show()
            }
    }

    private fun removeCamera() {
        val serialNumber = edtSerialNumber.text.toString().trim()

        if (serialNumber.isEmpty()) {
            Toast.makeText(this, "Enter Serial Number to Remove Camera", Toast.LENGTH_SHORT).show()
            return
        }

        database.reference.child("Cameras").child(serialNumber).removeValue()
            .addOnSuccessListener {
                Toast.makeText(this, "Camera Removed Successfully!", Toast.LENGTH_SHORT).show()
                stopProcessing() // Stop Processing
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to Remove Camera", Toast.LENGTH_SHORT).show()
            }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onCameraFrame(inputFrame: Mat?): Mat {
        inputFrame?.let {
            // Convert Frame to Tensor
            val imageTensor = convertMatToTensor(it)

            // Run Animal Detection Model
            val animalOutput = ModelHelper.runAnimalDetection(imageTensor)

            // Run Worker Face Detection Model
            val workerOutput = ModelHelper.runWorkerFaceDetection(imageTensor)

            // Check If Animal is Detected
            if (animalOutput != null && detectAnimal(animalOutput)) {
                handleDetection("Animal Detected", "Leopard", "Main Gate")
            }

            // Check If Unauthorized Worker is Detected
            if (workerOutput != null && detectWorker(workerOutput)) {
                handleDetection("Unauthorized Worker Detected", "Unknown Person", "South Zone")
            }
        }
        return inputFrame!!
    }

    private fun convertMatToTensor(mat: Mat): Tensor {
        // Convert OpenCV Mat to PyTorch Tensor (Simplified Example)
        val buffer = ByteBuffer.allocate(mat.total().toInt() * mat.channels())
        return Tensor.fromBlob(buffer, longArrayOf(1, 3, mat.rows().toLong(), mat.cols().toLong()))
    }

    private fun detectAnimal(output: Tensor): Boolean {
        // Custom Logic to check if animal is detected
        return true // Replace with actual model detection logic
    }

    private fun detectWorker(output: Tensor): Boolean {
        // Custom Logic to check if unauthorized worker is detected
        return true // Replace with actual model detection logic
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun handleDetection(title: String, detectedType: String, location: String) {
        // Get Current Date and Time
        val timestamp = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())

        // Save Detection Data to Firebase
        val detectionData = mapOf(
            "timestamp" to timestamp,
            "location" to location,
            "detectedType" to detectedType
        )
        database.reference.child("Detections").push().setValue(detectionData)

        // Trigger Alert Notification
        triggerAlertNotification(title)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun triggerAlertNotification(message: String) {
        val channelId = "wildwatch_alert"
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Create Notification Channel (For Android 8+)
        val channel = NotificationChannel(channelId, "WildWatch Alerts", NotificationManager.IMPORTANCE_HIGH)
        notificationManager.createNotificationChannel(channel)

        val notification = Notification.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_hardware)
            .setContentTitle("WildWatch Alert 🚨")
            .setContentText(message)
            .setPriority(Notification.PRIORITY_MAX)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(1, notification)

        // Set System Volume to Maximum
        val audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0)
    }

    private fun startProcessing(rtspUrl: String) {
        Toast.makeText(this, "Processing Camera Feed...", Toast.LENGTH_SHORT).show()
        // TODO: Start RTSP Stream and process frames using OpenCV
    }

    private fun stopProcessing() {
        Toast.makeText(this, "Camera Disconnected. Stopping Processing.", Toast.LENGTH_SHORT).show()
        // TODO: Stop processing the camera feed
    }

    override fun onDestroy() {
        super.onDestroy()
        stopProcessing()
    }
}

// Data Class for Storing Camera Info in Firebase

