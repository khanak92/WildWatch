package com.example.wildwatch1

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.ImageView
import android.widget.TextView


class trendAnalysis : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trend_analysis)

        val backButton = findViewById<ImageView>(R.id.backButton)
        val title = findViewById<TextView>(R.id.headerTitle)

        backButton.setOnClickListener {
            finish() // closes activity on back press
        }

        // Optional: Logic to load actual graph if available
        val graphPlaceholder = findViewById<TextView>(R.id.graphPlaceholder)
        // graphPlaceholder.text = "Graph Loaded"
    }
}






