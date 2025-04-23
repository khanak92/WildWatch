package com.example.wildwatch1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ScrollView
import androidx.fragment.app.Fragment
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

class VideoFragment : Fragment() {
    private val videoIds = listOf("9-zvJyrEEDE", "axcPoS2sF0E", "XvW9CiBQgYE","829YuVH1dg8","3rizxfyHPxs","OMkEVX23BdM","7jac_K-XB5A","W3FaKz5WYAE")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val scroll = ScrollView(requireContext())
        val layout = LinearLayout(requireContext())
        layout.orientation = LinearLayout.VERTICAL
        layout.setPadding(16, 16, 16, 16)

        videoIds.forEach { id ->
            val playerView = YouTubePlayerView(requireContext())
            playerView.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                600
            ).apply { setMargins(0, 16, 0, 16) }

            lifecycle.addObserver(playerView)

            playerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    youTubePlayer.loadVideo(id, 0f)
                }
            })

            layout.addView(playerView)
        }


        scroll.addView(layout)
        return scroll
    }
}
