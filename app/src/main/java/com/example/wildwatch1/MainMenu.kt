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
        R.drawable.lion,
        R.drawable.tiger,
        R.drawable.leopard,
        R.drawable.jaguar,
        R.drawable.snow,
        R.drawable.cheetah,
        R.drawable.cougar,
        R.drawable.clouded,

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
        val animalNames = listOf("Lion", "Tiger", "Leopard", "Jaguar", "Snow Leopard","Cheetah", "Cougar", "Clouded Leopard")
        val animalDescriptions = listOf(
            "The lion, often called the \"King of the Jungle,\" is a social big cat found in Africa and a small population in India's Gir Forest. Males are recognizable by their majestic manes, which range from blonde to black, while females are maneless and do most of the hunting. Lions live in prides, with females working together to take down prey like zebras and wildebeests. They are powerful but lack stamina, relying on short bursts of speed (up to 50 mph) and teamwork. Their roar can be heard up to 5 miles away, serving as a territorial warning. However, lions avoid confrontations with larger prey like buffaloes and elephants. To stay safe in lion territory, never run—instead, stand tall, make noise, and back away slowly. Habitat loss and human conflict threaten their survival.",
            "The tiger is the largest big cat, with the Siberian tiger weighing up to 660 lbs. Known for their striking orange coats with black stripes (each pattern is unique), tigers are solitary hunters found across Asia. Unlike lions, they prefer dense forests and are excellent swimmers, often cooling off in water. Tigers ambush prey with a powerful pounce, crushing windpipes with a 1,050 psi bite force. However, they overheat quickly and avoid long chases. Attacks on humans are rare but can happen if tigers feel threatened or are starving. To avoid danger in tiger habitats, wear masks on the back of your head (tigers prefer ambushing from behind) and make loud noises. Poaching and deforestation have pushed tigers to near extinction.",
            "Leopards are the most adaptable big cats, thriving in jungles, mountains, and even urban areas like Mumbai. Their golden coats with rosette patterns provide camouflage, while melanistic leopards (black panthers) are common in dense forests. Unlike lions, leopards are solitary and incredibly strong—able to drag prey twice their weight up trees to avoid scavengers. They are stealthy hunters, relying on surprise rather than speed. However, they often lose kills to lions or hyenas. In areas with leopards, avoid walking alone at night and secure livestock. Habitat destruction and poaching for their beautiful fur threaten their survival.",
            "The jaguar, the largest cat in the Americas, dominates the rainforests of the Amazon and Pantanal. With a stocky build and a coat covered in rosettes (some with spots inside), jaguars are apex predators known for their incredible bite force (1,500 psi)—enough to crush skulls. Unlike other big cats, they often kill by piercing the brain or spine. Jaguars are strong swimmers and hunt caimans, deer, and even anacondas. They are solitary and territorial, marking their domain with deep roars. Deforestation and hunting for their pelts have made them near-threatened. To avoid jaguars in the wild, stay on marked trails and avoid dense riverbanks at dawn/dusk.",
            "The elusive \"Ghost of the Mountains,\" the snow leopard, inhabits the high altitudes of the Himalayas and Central Asia. Its thick, gray-white fur with dark rosettes provides perfect camouflage against rocky slopes. Unlike other big cats, snow leopards cannot roar but communicate with chuffs and growls. They are incredible climbers, using their long tails for balance while leaping across cliffs. Preying on blue sheep and ibex, they rely on stealth rather than speed. Climate change and poaching for their fur and bones have left only about 4,000 in the wild. If you encounter one, consider yourself lucky—they rarely attack humans.",
            "The cheetah, the fastest land animal (70 mph), is built for speed with a slender body, long legs, and non-retractable claws for traction. Unlike other big cats, cheetahs have tear-marked faces and solid black spots. They hunt by daylight, using their incredible acceleration to chase down gazelles. However, they are fragile—losing kills to lions or hyenas and often succumbing to injuries. Cheetahs are solitary (except for mothers with cubs) and avoid fights. Habitat loss and illegal pet trade have critically endangered them. In the wild, never approach a cheetah—they’re shy but may defend their meal.",
            "Also called puma or mountain lion, the cougar is the largest \"small cat\" (purring but not roaring) in the Americas. With a tan coat and no markings, they resemble oversized house cats. Cougars are ambush predators, leaping 20 feet onto deer or elk. They are solitary and highly adaptable, living from Canada to Patagonia. Unlike other big cats, they rarely attack humans but may stalk joggers or cyclists. To avoid cougar encounters, hike in groups and make noise. Urban expansion has forced them into closer contact with humans.",
            "The clouded leopard, named for its cloud-like coat patterns, is a tree-dwelling cat of Southeast Asia. With the longest canine teeth relative to body size, it’s often called a \"modern saber-tooth.\" Its flexible ankles allow it to climb down trees headfirst—a rare skill among cats. Solitary and nocturnal, it preys on monkeys, birds, and deer. Deforestation and poaching for its beautiful pelt have made it vulnerable. If you’re in its habitat, look up—these cats are masters of camouflage in the canopy.",

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
