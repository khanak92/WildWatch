package com.example.wildwatch1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.firebase.messaging.FirebaseMessaging
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityOptionsCompat
import android.view.View


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
        val animation = android.view.animation.AnimationUtils.loadAnimation(this, R.anim.fade_in_slide_up)
        rootView.startAnimation(animation)

        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("FCM", "Fetching FCM registration token failed", task.exception)
                return@addOnCompleteListener
            }
            val token = task.result
            //Toast.makeText(this, "Token: $token", Toast.LENGTH_SHORT).show()
            Log.d("FCM_TOKEN", token)
        }

        NotificationUtils.createNotificationChannel(this)

        greetingText = findViewById(R.id.greetingText)
        greetingText.text = "Hi Mr. Areeb 👋"

        viewPager = findViewById(R.id.imageCarousel)

        val adapter = ImageCarouselAdapter(
            context = this,
            images = images,
            onItemClick = { position ->
                openAnimalInfo(position)
            }
        )
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit = 3
        viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        // 🔥 Apply cool zoom effect
        applyViewPagerAnimation()

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

        // 🎬 Shared element transition
        val imageView = viewPager.findViewWithTag<ImageView>("image_$position")
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
            this,
            imageView,
            "animalImageTransition_$position" // transitionName must match!
        )
        startActivity(intent, options.toBundle())
    }

    private fun setButtonListeners() {
        findViewById<Button>(R.id.btnPrecautionaryMeasures).setOnClickListener {
            bounce(it)
            startActivity(Intent(this, precautionaryMeasures2::class.java))
        }
        findViewById<Button>(R.id.btnRecentlyDetected).setOnClickListener {
            bounce(it)
            startActivity(Intent(this, recent_locations::class.java))
        }
        findViewById<Button>(R.id.btnHardwareManagement).setOnClickListener {
            bounce(it)
            startActivity(Intent(this, HardwareManagement::class.java))
        }
        findViewById<Button>(R.id.btnTrendAnalysis).setOnClickListener {
            bounce(it)
            startActivity(Intent(this, TrendAnalysisActivity2::class.java))
        }
        findViewById<Button>(R.id.btnUserProfile).setOnClickListener {
            bounce(it)
            startActivity(Intent(this, userProfile::class.java))
        }
        findViewById<Button>(R.id.btnLiveStream).setOnClickListener {
            bounce(it)
            startActivity(Intent(this, liveStream::class.java))
        }
        findViewById<Button>(R.id.btnNotificationSettings).setOnClickListener {
            bounce(it)
            startActivity(Intent(this, notificationMgt::class.java))
        }
        //findViewById<Button>(R.id.btnContactWildlifeDepartment).setOnClickListener {
        //   startActivity(Intent(this, contactWildlifeDepartment::class.java))
        //}
    }
}
