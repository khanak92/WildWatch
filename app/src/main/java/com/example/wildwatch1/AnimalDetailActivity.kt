package com.example.wildwatch1

import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class AnimalDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animal_detail)

        val animalName = intent.getStringExtra("animalName") ?: "Animal"
        val animalDesc = intent.getStringExtra("animalDesc") ?: "No description available."
        val animalImage = intent.getIntExtra("animalImage", R.drawable.placeholder_image)

        val animalImageView = findViewById<ImageView>(R.id.animalImage)
        val nameTextView = findViewById<TextView>(R.id.animalName)
        val descTextView = findViewById<TextView>(R.id.animalDescription)

        Glide.with(this)
            .load(animalImage)
            .override(1080, 600) // Safely resize to avoid crash
            .into(animalImageView)

        nameTextView.text = animalName
        descTextView.text = animalDesc

        // Add simple entrance animation 🌀
        val fadeIn = AnimationUtils.loadAnimation(this, android.R.anim.fade_in)
        nameTextView.startAnimation(fadeIn)
        descTextView.startAnimation(fadeIn)
    }
}
