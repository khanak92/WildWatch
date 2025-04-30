package com.example.wildwatch1

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat

@Suppress("DEPRECATION")
class precautionaryMeasures2 : AppCompatActivity() {

    private lateinit var btnText: Button
    private lateinit var btnVideos: Button
    private lateinit var contentContainer: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = ContextCompat.getColor(this, R.color.primaryDark)

        setContentView(R.layout.activity_precautionary_measures2)

        btnText = findViewById(R.id.btnText)
        btnVideos = findViewById(R.id.btnVideos)
        contentContainer = findViewById(R.id.contentContainer)

        showTextFragment()

        btnText.setOnClickListener {
            showTextFragment()
        }

        btnVideos.setOnClickListener {
            showVideoFragment()
        }
    }

    private fun showTextFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.contentContainer, TextFragment())
            .commit()
    }

    private fun showVideoFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.contentContainer, VideoFragment())
            .commit()
    }
}
