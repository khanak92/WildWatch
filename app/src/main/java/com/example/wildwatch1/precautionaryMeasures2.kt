package com.example.wildwatch1

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.FrameLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class precautionaryMeasures2 : AppCompatActivity() {

    private lateinit var btnText: Button
    private lateinit var btnVideos: Button
    private lateinit var contentContainer: FrameLayout

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_precautionary_measures2)

        btnText = findViewById(R.id.btnText)
        btnVideos = findViewById(R.id.btnVideos)
        contentContainer = findViewById(R.id.contentContainer)

        supportFragmentManager.beginTransaction()
            .replace(R.id.contentContainer, TextFragment())
            .commit()

        btnText.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.contentContainer, TextFragment())
                .commit()
        }

        btnVideos.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.contentContainer, VideoFragment())
                .commit()
        }
    }
}
