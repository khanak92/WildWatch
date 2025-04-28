package com.example.wildwatch1

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.media.AudioManager
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.NotificationCompat
import android.view.animation.ScaleAnimation
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Animation


class notificationMgt : AppCompatActivity() {

    private lateinit var btnMuteUnmute: Button
    private lateinit var btnTestNotification: Button
    private lateinit var btnTestShare: Button
    private lateinit var btnChangeTimer: Button
    private val PREFS_NAME = "WildWatchPrefs"
    private val MUTE_KEY = "isMuted"
    private lateinit var sharedPreferences: SharedPreferences

    private var notificationTimer = 120000L // Default 2 minutes in milliseconds
    private val timerOptions = arrayOf(120000L, 150000L, 180000L, 210000L, 240000L, 270000L, 300000L) // 2 min to 5 min (30s gap)
    private var timerIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification_mgt)
        sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        btnMuteUnmute = findViewById(R.id.btnMuteUnmute)
        btnTestNotification = findViewById(R.id.btnTestNotification)
        btnTestShare = findViewById(R.id.btnTestShare)
        btnChangeTimer = findViewById(R.id.btnChangeTimer)

        btnMuteUnmute.setOnClickListener {
            showMuteUnmuteDialog()
        }

        btnTestNotification.setOnClickListener {
            triggerTestNotification()
        }

        btnTestShare.setOnClickListener {
            shareTestData()
        }

        btnChangeTimer.setOnClickListener {
            changeNotificationTimer()
        }
    }

    // 1️⃣ Show Dialog for Mute/Unmute
    private fun showMuteUnmuteDialog() {
        val layoutInflater = LayoutInflater.from(this)
        val popupView: View = layoutInflater.inflate(R.layout.dialog_mute_unmute, null)

        val popupWindow = PopupWindow(
            popupView,
            ConstraintLayout.LayoutParams.WRAP_CONTENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT,
            true
        )

        // 🔄 Scale animation
        val scale = ScaleAnimation(
            0.7f, 1.0f, // X scale
            0.7f, 1.0f, // Y scale
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        ).apply {
            duration = 300
            interpolator = AccelerateDecelerateInterpolator()
        }

        popupView.startAnimation(scale)
        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0)

        popupView.setOnClickListener {
            popupWindow.dismiss()
        }

        // 🔇 Persistent mute state using SharedPreferences
        val sharedPrefs = getSharedPreferences("WildWatchPrefs", Context.MODE_PRIVATE)
        val isMuted = sharedPrefs.getBoolean("isMuted", false)

        val txtStatus = popupView.findViewById<TextView>(R.id.txtMuteStatus)
        val btnToggle = popupView.findViewById<Button>(R.id.btnToggleSound)

        txtStatus.text = if (isMuted) "App Sound is Muted" else "App Sound is Unmuted"
        btnToggle.text = if (isMuted) "Unmute" else "Mute"

        btnToggle.setOnClickListener {
            val newMuteState = !isMuted
            sharedPrefs.edit().putBoolean("isMuted", newMuteState).apply()

            val audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
            audioManager.ringerMode = if (newMuteState) AudioManager.RINGER_MODE_SILENT else AudioManager.RINGER_MODE_NORMAL

            Toast.makeText(this, if (newMuteState) "Sound Muted" else "Sound Unmuted", Toast.LENGTH_SHORT).show()
            popupWindow.dismiss()
        }
    }


    // 2️⃣ Trigger Test Notification
    private fun triggerTestNotification() {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "wildwatch_alert"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, "WildWatch Alerts", NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_hardware)
            .setContentTitle("WildWatch Alert")
            .setContentText("This is a test notification!")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(1, notification)
    }

    // 3️⃣ Share Test Data
    private fun shareTestData() {
        val message = "🚨 Wildlife detected on Camera 1! Stay Alert. 📢"
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, message)
            type = "text/plain"
        }
        startActivity(Intent.createChooser(shareIntent, "Share Alert via"))
    }

    // 4️⃣ Change Notification Timer
    private fun changeNotificationTimer() {
        timerIndex = (timerIndex + 1) % timerOptions.size
        notificationTimer = timerOptions[timerIndex]

        val minutes = notificationTimer / 60000
        btnChangeTimer.text = "Change Notification Timer (${minutes} min)"
        Toast.makeText(this, "Timer changed to $minutes minutes", Toast.LENGTH_SHORT).show()
    }
}
