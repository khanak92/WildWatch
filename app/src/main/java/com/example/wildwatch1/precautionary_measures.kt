package com.example.wildwatch1

import VideoAdapter
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class precautionary_measures : AppCompatActivity() {

    private lateinit var videoRecyclerView: RecyclerView
    private lateinit var btnVideos: Button
    private lateinit var btnText: Button

    // List of YouTube video IDs
    private val videoIds = listOf(
        "9-zvJyrEEDE",
        "axcPoS2sF0E",
        "XvW9CiBQgYE",
        "829YuVH1dg8",
        "3rizxfyHPxs",
        "OMkEVX23BdM",
        "7jac_K-XB5A",
        "W3FaKz5WYAE"
    )

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_precautionary_measures)

        // Initialize Buttons
        btnVideos = findViewById(R.id.btnVideos)
        btnText = findViewById(R.id.btnText)

        // Handle Button Click to Switch Between Videos and Text
        btnVideos.setOnClickListener {
            // Reload the same activity (videos remain visible)
            recreate()
        }

        btnText.setOnClickListener {
            // Navigate to the Text-Based Precautionary Measures Activity
            startActivity(Intent(this, precautionaryMeasures2::class.java))
        }

        // Ensure edge-to-edge insets work correctly
        val mainView = findViewById<ConstraintLayout>(R.id.main)
        mainView?.let { view ->
            ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }
        }

        // Initialize RecyclerView for YouTube videos
        videoRecyclerView = findViewById(R.id.videoRecyclerView)
        videoRecyclerView.layoutManager = LinearLayoutManager(this)
        videoRecyclerView.adapter = VideoAdapter(videoIds, lifecycle)
    }
}
