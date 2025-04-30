package com.example.wildwatch1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.viewpager2.widget.ViewPager2
import com.google.firebase.messaging.FirebaseMessaging
import java.util.Calendar

class MainMenu : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var greetingText: TextView

    private val images = listOf(
        R.drawable.lion_family1,
        R.drawable.lion_family2,
        R.drawable.lion_family3,
        R.drawable.lion_family4,
        R.drawable.lion_family5
    )

    private fun bounce(view: View) {
        view.animate()
            .scaleX(0.95f)
            .scaleY(0.95f)
            .setDuration(100)
            .withEndAction {
                view.animate()
                    .scaleX(1f)
                    .scaleY(1f)
                    .setDuration(100)
                    .start()
            }
            .start()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        val rootView = findViewById<View>(R.id.drawerLayout)
        val animation = AnimationUtils.loadAnimation(this, R.anim.fade_in_slide_up)
        rootView.startAnimation(animation)

        NotificationUtils.createNotificationChannel(this)

        // ✅ Firebase FCM Token
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("FCM", "Fetching FCM registration token failed", task.exception)
                return@addOnCompleteListener
            }
            val token = task.result
            Log.d("FCM_TOKEN", token)
        }

        // 👋 Personalized greeting
        greetingText = findViewById(R.id.greetingText)
        val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        val greeting = when (hour) {
            in 0..11 -> "Good Morning"
            in 12..17 -> "Good Afternoon"
            else -> "Good Evening"
        }
        greetingText.text = "$greeting, Mr. Areeb 👋"

        // 📷 Carousel Setup
        viewPager = findViewById(R.id.imageCarousel)
        viewPager.adapter = ImageCarouselAdapter(this, images) { position ->
            openAnimalInfo(position)
        }
        viewPager.offscreenPageLimit = 3
        viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        applyViewPagerAnimation()

        // 🎯 Button Listeners
        setButtonListeners()
    }

    private fun applyViewPagerAnimation() {
        viewPager.setPageTransformer { page, position ->
            val scale = 0.85f + (1 - Math.abs(position)) * 0.15f
            page.scaleY = scale
            page.scaleX = scale
            page.alpha = 0.5f + (1 - Math.abs(position)) * 0.5f
        }
    }

    private fun openAnimalInfo(position: Int) {
        val animalNames = listOf("Lion", "Tiger", "Leopard", "Elephant", "Giraffe")
        val animalDescriptions = listOf(
            "The lion is a species in the family Felidae and a member of the genus Panthera.",
            "The tiger is the largest living cat species and a member of the genus Panthera.",
            "The leopard is one of the five extant species in the genus Panthera.",
            "Elephants are the largest existing land animals.",
            "The giraffe is the tallest living terrestrial animal."
        )

        val intent = Intent(this, AnimalDetailActivity::class.java).apply {
            putExtra("animalName", animalNames[position])
            putExtra("animalDesc", animalDescriptions[position])
            putExtra("animalImage", images[position])
        }

        val imageView = viewPager.findViewWithTag<ImageView>("image_$position")
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
            this,
            imageView,
            "animalImageTransition_$position"
        )
        startActivity(intent, options.toBundle())
    }

    private fun setButtonListeners() {
        val buttons = listOf(
            R.id.btnPrecautionaryMeasures to precautionaryMeasures2::class.java,
            R.id.btnRecentlyDetected to recent_locations::class.java,
            R.id.btnHardwareManagement to HardwareManagement::class.java,
            R.id.btnTrendAnalysis to TrendAnalysisActivity2::class.java,
            R.id.btnUserProfile to userProfile::class.java,
            R.id.btnLiveStream to liveStream::class.java,
            R.id.btnNotificationSettings to notificationMgt::class.java
        )

        buttons.forEach { (id, target) ->
            findViewById<Button>(id).setOnClickListener {
                bounce(it)
                startActivity(Intent(this, target))
            }
        }
    }
}
