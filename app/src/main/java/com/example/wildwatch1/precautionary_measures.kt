package com.example.wildwatch1

import VideoAdapter
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class precautionary_measures : AppCompatActivity() {

    private lateinit var videoRecyclerView: RecyclerView

    //list fo videos
    private val videoIds = listOf(
        "123",
        "345",
        "678"
    )

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_precautionary_measures)

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
