package com.example.wildwatch1

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.view.View

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Find the main layout view
        val mainView = findViewById<View>(R.id.main)
        if (mainView != null) {
            // Apply window insets to the main view if it's not null
            ViewCompat.setOnApplyWindowInsetsListener(mainView) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }
        } else {
            // Log an error if the main view is null
            Log.e("MainActivity", "View with ID 'main' is null. Check layout file for correct ID.")
        }

        // Transition to LoginSignup Activity after 3 seconds
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, LoginSignup::class.java)
            startActivity(intent)
            finish() // Close MainActivity to prevent back navigation
        }, 6000)
    }
}
