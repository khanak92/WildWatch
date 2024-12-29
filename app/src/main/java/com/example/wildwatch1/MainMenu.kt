package com.example.wildwatch1

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class MainMenu : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout

    @SuppressLint("MissingInflatedId", "CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        drawerLayout = findViewById(R.id.drawerLayout)  // Correct ID for the DrawerLayout
        val navigationView = findViewById<NavigationView>(R.id.navigationView)  // Correct ID for the NavigationView


        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, R.string.open, R.string.close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navigationView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_precautionary_measures -> {
                    startActivity(Intent(this, precautionary_measures::class.java))
                }
                R.id.nav_recently_detected -> {
                    startActivity(Intent(this, recent_locations::class.java))
                }
                R.id.nav_hardware_management -> {
                    startActivity(Intent(this, hardwareMgt::class.java))
                }
                R.id.nav_trend_analysis -> {
                    startActivity(Intent(this, trendAnalysis::class.java))
                }
                R.id.nav_user_profile -> {
                    startActivity(Intent(this, userProfile::class.java))
                }
                R.id.nav_live_stream -> {
                    startActivity(Intent(this, liveStream::class.java))
                }
                R.id.nav_notification_settings -> {
                    startActivity(Intent(this, notificationMgt::class.java))
                }
                else -> {
                    Toast.makeText(this, "Unknown option selected", Toast.LENGTH_SHORT).show()
                }
            }
            drawerLayout.closeDrawers() // Close the drawer after selection
            true
        }
    }
}
