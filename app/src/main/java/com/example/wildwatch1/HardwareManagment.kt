package com.example.wildwatch1

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.annotation.OptIn
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.media3.common.MediaItem
import androidx.media3.common.MimeTypes
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.rtsp.RtspMediaSource
import androidx.media3.ui.PlayerView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.*
import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging


class HardwareManagement : AppCompatActivity() {

    private lateinit var edtRtspLink: EditText
    private lateinit var edtCameraLocation: EditText
    private lateinit var btnAddCamera: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var playerView: PlayerView
    private lateinit var player: ExoPlayer
    private lateinit var database: DatabaseReference
    private val cameraList = mutableListOf<Camera>()
    private lateinit var cameraAdapter: CameraAdapter

    @OptIn(UnstableApi::class)
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hardware_managment)

        edtRtspLink = findViewById(R.id.edtRtspLink)
        edtCameraLocation = findViewById(R.id.edtCameraLocation)
        btnAddCamera = findViewById(R.id.btnAddCamera)
        recyclerView = findViewById(R.id.recyclerView)
        playerView = findViewById(R.id.playerView)

        database = FirebaseDatabase.getInstance().getReference("Cameras")

        recyclerView.layoutManager = LinearLayoutManager(this)
        cameraAdapter = CameraAdapter(cameraList) { camera ->
            showDeleteConfirmationDialog(camera)
            playRTSPStream(camera.rtspLink)
        }
        recyclerView.adapter = cameraAdapter

        btnAddCamera.setOnClickListener {
            addCameraToFirebase()
        }

        loadCamerasFromFirebase()
    }



    private fun loadCamerasFromFirebase() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                cameraList.clear()
                for (cameraSnapshot in snapshot.children) {
                    val camera = cameraSnapshot.getValue(Camera::class.java)
                    camera?.let { cameraList.add(it) }
                }
                cameraAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                showSnackbar("Failed to load cameras", false)
            }
        })
    }

    @OptIn(UnstableApi::class)
    private fun addCameraToFirebase() {
        val rtspLink = edtRtspLink.text.toString().trim()
        val location = edtCameraLocation.text.toString().trim()
        val timestamp = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())

        if (rtspLink.isEmpty() || location.isEmpty()) {
            showSnackbar("Please enter all fields", false)
            return
        }

        val cameraId = database.push().key ?: return
        val cameraData = Camera(cameraId, rtspLink, location, timestamp)

        database.child(cameraId).setValue(cameraData).addOnSuccessListener {
            showSnackbar("Camera added successfully", true)
            edtRtspLink.text?.clear()
            edtCameraLocation.text?.clear()

            playRTSPStream(rtspLink)
        }.addOnFailureListener {
            showSnackbar("Failed to add camera", false)
        }
    }

    private fun removeCamera(camera: Camera) {
        database.child(camera.id).removeValue().addOnSuccessListener {
            showSnackbar("Camera removed successfully", true)
            cameraAdapter.removeCamera(camera)
        }.addOnFailureListener {
            showSnackbar("Failed to remove camera", false)
        }
    }

    private fun showDeleteConfirmationDialog(camera: Camera) {
        AlertDialog.Builder(this)
            .setTitle("Delete Camera")
            .setMessage("Are you sure you want to remove this camera?")
            .setPositiveButton("Yes") { _, _ -> removeCamera(camera) }
            .setNegativeButton("Cancel", null)
            .show()
    }


    @UnstableApi
    private fun playRTSPStream(rtspUrl: String) {
        val mediaItem = MediaItem.fromUri(rtspUrl)

        val mediaSource = RtspMediaSource.Factory().createMediaSource(mediaItem)

        player = ExoPlayer.Builder(this).build()
        playerView.player = player
        player.setMediaSource(mediaSource)
        player.prepare()
        player.playWhenReady = true
    }

    private fun showSnackbar(message: String, success: Boolean) {
        val rootView: View = findViewById(R.id.main)
        val snackbar = Snackbar.make(rootView, message, Snackbar.LENGTH_LONG)
        snackbar.setBackgroundTint(
            resources.getColor(
                if (success) android.R.color.holo_green_light else android.R.color.holo_red_light,
                theme
            )
        )
        snackbar.show()
    }




}

        //  Camera data
data class Camera(
    val id: String = "",
    val rtspLink: String = "",
    val location: String = "",
    val timestamp: String = ""
)
