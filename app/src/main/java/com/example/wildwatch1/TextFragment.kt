package com.example.wildwatch1

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment

class TextFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val context = requireContext()

        // ScrollView container
        val scrollView = ScrollView(context).apply {
            setPadding(dp(16), dp(16), dp(16), dp(16))
        }

        // LinearLayout inside ScrollView
        val layout = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
        }

        val texts = listOf(
            "⚪ Keep a Safe Distance" to "Maintain distance and avoid feeding or touching animals.",
            "⚪ Avoid Sudden Movements" to "Move calmly, avoid running or startling animals.",
            "⚪ What to do and not do?" to "Wild animals generally avoid human contact, but if you do see an animal in the wild, maintain your distance. Don’t attempt to feed, catch, or pet a wild animal...",
            "⚪ Diseases caused by attacks of wild animals?" to "Infections like rabies, leptospirosis, and bacterial wounds are common.",
            "⚪ How to avoid areas merged with wildlife?" to "Avoid forest edges at night, follow posted warnings, and use wildlife-aware navigation tools.",
            "⚪ How zoning of areas can reduce conflicts?" to "Urban planning with green buffers and alert systems helps reduce conflicts.",
            "⚪ How to respond to a wild animal attack?" to "Stay calm, avoid eye contact, back away slowly, and never run.",
            "⚪ Are wild animals scared of humans?" to "Generally yes, but habituation can reduce their fear. Never approach them.",
            "⚪ Do not keep wildlife as pets" to "It's dangerous and often illegal. Wild animals belong in the wild.",
            "⚪ Do not use the internet for wildlife care advice" to "Consult professionals or wildlife authorities instead.",
            "⚪ How do I transport a wild animal?" to "Only trained professionals should do this. Contact wildlife rescue."
        )

        for ((title, detail) in texts) {
            // Title TextView
            val titleView = TextView(context).apply {
                text = title
                setTextColor(Color.WHITE)
                setTextSize(TypedValue.COMPLEX_UNIT_SP, 19f)
                setTypeface(null, Typeface.BOLD)
                setPadding(dp(17), dp(13), dp(17), dp(13))
                background = ContextCompat.getDrawable(context, R.drawable.title_bg)
                isClickable = true
            }

            // Detail TextView
            val detailView = TextView(context).apply {
                text = detail
                setTextColor(Color.BLACK)
                setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
                setPadding(dp(17), dp(11), dp(17), dp(17))
                visibility = View.GONE
                background = ContextCompat.getDrawable(context, R.drawable.detail_bg)
            }

            // Toggle with animation
            titleView.setOnClickListener {
                val isVisible = detailView.isVisible
                detailView.visibility = if (isVisible) View.GONE else View.VISIBLE
                if (!isVisible) {
                    val fadeIn = AlphaAnimation(0f, 1f).apply {
                        duration = 300
                    }
                    detailView.startAnimation(fadeIn)
                }
            }

            layout.addView(titleView)
            layout.addView(detailView)

            // Optional: Add spacing between blocks
            val spacer = View(context).apply {
                layoutParams = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    dp(8)
                )
            }
            layout.addView(spacer)
        }

        scrollView.addView(layout)
        return scrollView
    }

    private fun dp(value: Int): Int {
        val scale = resources.displayMetrics.density
        return (value * scale).toInt()
    }
}
