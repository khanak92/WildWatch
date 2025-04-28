package com.example.wildwatch1

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class AnimalDetailActivity : AppCompatActivity() {

    private lateinit var animalImage: ImageView
    private lateinit var animalName: TextView
    private lateinit var animalDesc: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animal_detail)

        animalImage = findViewById(R.id.animalImage)
        animalName = findViewById(R.id.animalName)
        animalDesc = findViewById(R.id.animalDescription)

        // Retrieve data from intent
        val name = intent.getStringExtra("animalName")
        val desc = intent.getStringExtra("animalDesc")
        val imageRes = intent.getIntExtra("animalImage", -1)

        if (name != null) {
            animalName.text = name
        }
        if (desc != null) {
            animalDesc.text = desc
        }
        if (imageRes != -1) {
            animalImage.setImageResource(imageRes)
        }

        // 🎬 Apply Animations
        val slideIn = android.view.animation.AnimationUtils.loadAnimation(this, R.anim.slide_in_bottom)
        val fadeIn = android.view.animation.AnimationUtils.loadAnimation(this, R.anim.fade_in)

        animalImage.startAnimation(fadeIn)
        animalName.startAnimation(slideIn)
        animalDesc.startAnimation(slideIn)
    }

}
