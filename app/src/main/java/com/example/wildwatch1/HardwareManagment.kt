package com.example.wildwatch1

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HardwareManagement : AppCompatActivity() {

    private lateinit var edtRtspLink: EditText
    private lateinit var edtCameraLocation: EditText
    private lateinit var btnAddCamera: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var cameraAdapter: CameraAdapter
    private lateinit var database: DatabaseReference

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hardware_managment)

        edtRtspLink = findViewById(R.id.edtRtspLink)
        edtCameraLocation = findViewById(R.id.edtCameraLocation)
        btnAddCamera = findViewById(R.id.btnAddCamera)
        recyclerView = findViewById(R.id.recyclerView)


        database = FirebaseDatabase.getInstance().getReference("Cameras")

        recyclerView.layoutManager = LinearLayoutManager(this)
        cameraAdapter = CameraAdapter(mutableListOf()) { camera ->
            removeCamera(camera) // ✅ Now passing function reference
        }

        recyclerView.adapter = cameraAdapter

        btnAddCamera.setOnClickListener {
            addCameraToFirebase()
        }
    }
    private fun removeCamera(camera: Camera) {
        database.child(camera.id).removeValue().addOnSuccessListener {
            Toast.makeText(this, "Camera removed successfully", Toast.LENGTH_SHORT).show()
            cameraAdapter.removeCamera(camera)
        }.addOnFailureListener {
            Toast.makeText(this, "Failed to remove camera", Toast.LENGTH_SHORT).show()
        }
    }


    private fun addCameraToFirebase() {
        val rtspLink = edtRtspLink.text.toString().trim()
        val location = edtCameraLocation.text.toString().trim()
        val timestamp = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())

        if (rtspLink.isEmpty() || location.isEmpty()) {
            Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val cameraId = database.push().key!!
        val cameraData = Camera(cameraId, rtspLink, location, timestamp)

        database.child(cameraId).setValue(cameraData).addOnSuccessListener {
            Toast.makeText(this, "Camera added successfully", Toast.LENGTH_SHORT).show()
            cameraAdapter.addCamera(cameraData)
            edtRtspLink.text.clear()
            edtCameraLocation.text.clear()
        }.addOnFailureListener {
            Toast.makeText(this, "Failed to add camera", Toast.LENGTH_SHORT).show()
        }
    }
}

// Camera Data Model
data class Camera(val id: String, val rtspLink: String, val location: String, val timestamp: String)
