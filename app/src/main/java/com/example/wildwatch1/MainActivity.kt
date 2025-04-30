package com.example.wildwatch1

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import androidx.core.content.ContextCompat

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        //installSplashScreen()
       window.statusBarColor = ContextCompat.getColor(this, R.color.primaryDark)

        super.onCreate(savedInstanceState)



        setContentView(R.layout.activity_main)
        val tagline = findViewById<TextView>(R.id.taglineText)

        // Fade in the tagline after 1 second
        tagline.animate()
            .alpha(1f)
            .setDuration(1200)
            .setStartDelay(1000)
            .start()

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainMenu::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }, 3000) // 3 seconds
    }


}
