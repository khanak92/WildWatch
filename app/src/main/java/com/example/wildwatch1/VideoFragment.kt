package com.example.wildwatch1

import VideoAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ScrollView
import androidx.core.view.setPadding
import androidx.fragment.app.Fragment
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

class VideoFragment : Fragment() {

    private val videoIds = listOf(
        "9-zvJyrEEDE" to "Wildlife Protection Basics", "axcPoS2sF0E" to "Wildlife Protection Basics", "XvW9CiBQgYE" to "Wildlife Protection Basics", "829YuVH1dg8" to "Wildlife Protection Basics",
        "3rizxfyHPxs" to "Wildlife Protection Basics", "OMkEVX23BdM" to "Wildlife Protection Basics", "7jac_K-XB5A" to "Wildlife Protection Basics", "W3FaKz5WYAE" to "Wildlife Protection Basics",
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val context = requireContext()

        // Scroll container
        val scrollView = ScrollView(context).apply {
            setPadding(dp(16))
        }


        // Vertical LinearLayout inside ScrollView
        val layout = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
        }

        // Add each YouTube player with consistent styling
        videoIds.forEach { videoId ->
            val playerView = YouTubePlayerView(context).apply {
                layoutParams = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    dp(220)
                ).apply {
                    setMargins(0, dp(12), 0, dp(12))
                }

                // Optional: Rounded corners using ViewOutlineProvider (for modern look)
                clipToOutline = true
                background = resources.getDrawable(R.drawable.video_background, null)

                // Lifecycle awareness
                lifecycle.addObserver(this)

                // Load video when ready
                addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        youTubePlayer.loadVideo(videoId.toString(), 0f)
                    }
                })
            }

            layout.addView(playerView)
        }

        scrollView.addView(layout)
        return scrollView
    }

    private fun dp(value: Int): Int {
        val scale = resources.displayMetrics.density
        return (value * scale).toInt()
    }
}
