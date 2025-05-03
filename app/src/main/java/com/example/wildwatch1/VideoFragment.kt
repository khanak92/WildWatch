package com.example.wildwatch1

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
        "9-zvJyrEEDE", "axcPoS2sF0E", "XvW9CiBQgYE", "829YuVH1dg8",
        "3rizxfyHPxs", "OMkEVX23BdM", "7jac_K-XB5A", "W3FaKz5WYAE"
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val context = requireContext()

        val scrollView = ScrollView(context).apply {
            setPadding(dp(16))
        }

        val layout = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
        }

        videoIds.forEach { videoId ->
            val playerView = YouTubePlayerView(context).apply {
                layoutParams = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    dp(220)
                ).apply {
                    setMargins(0, dp(12), 0, dp(12))
                }

                // Optional: Rounded corners (if your drawable supports it)
                clipToOutline = true
                background = resources.getDrawable(R.drawable.video_background, null)

                lifecycle.addObserver(this)

                addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        youTubePlayer.cueVideo(videoId, 0f)
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
