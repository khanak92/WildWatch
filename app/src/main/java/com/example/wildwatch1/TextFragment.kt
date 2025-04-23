package com.example.wildwatch1

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.core.view.isVisible

class TextFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val layout = LinearLayout(requireContext())
        layout.orientation = LinearLayout.VERTICAL
        layout.setPadding(16, 16, 16, 16)

        val texts = listOf(
            "🔸 Keep a Safe Distance" to "Maintain distance and avoid feeding or touching animals.",
            "🔸 Avoid Sudden Movements" to "Move calmly, avoid running or startling animals.",
            "🔸What to do and not do?" to "Wild animals generally avoid human contact, but if you do see an animal in the wild, maintain your distance. Don’t attempt to feed, catch,or pet a wild animal. Never approach wildlife babies or animal mothers with their babies; the mother’s protective response can be very fierce. Report injured or aggressive animals to authorities; don’t attempt to give aid to injured wildlife. If an injured animal approaches you, move slowly away.Mountain lion sightings are rare, but they have been known to attack humans. If you do encounter a mountain lion, don’t run. Stay calm and hold your position or back away slowly. Convince the animal that you’re not prey and that you might be dangerous. Face the lion and try to appear as large as possible by standing upright and raising your arms. If the lion acts aggressively, wave your arms and shout. Grab a stick or throw objects",
            "🔸 Diseases caused by the attach of wild animals?" to "Move calmly, avoid running or startling animals.",
            "🔸 How to avoid on the areas where population is merged with wild animals?" to "Move calmly, avoid running or startling animals.",
            "🔸 How zoning of areas can reduce the conflicts?" to "Move calmly, avoid running or startling animals.",
            "🔸 How to respond a wild animal attack?" to "Move calmly, avoid running or startling animals.",
            "🔸 Are wild animals scared of humans?" to "Move calmly, avoid running or startling animals.",
            "🔸 Do not keep wildlife as pets" to "Move calmly, avoid running or startling animals.",
            "🔸 Do not use the internet for wildlife care advice" to "Move calmly, avoid running or startling animals.",
            "🔸 How do I transport a wild animal?" to "Move calmly, avoid running or startling animals.",
        )

        for ((title, detail) in texts) {
            val titleView = TextView(requireContext()).apply {
                text = title
                setBackgroundColor(Color.parseColor("#FF8E3A"))
                setTextColor(Color.WHITE)
                setPadding(16, 16, 16, 16)
            }

            val detailView = TextView(requireContext()).apply {
                text = detail
                setPadding(16, 8, 16, 8)
                visibility = View.GONE
            }

            titleView.setOnClickListener {
                detailView.visibility = if (detailView.isVisible) View.GONE else View.VISIBLE
            }

            layout.addView(titleView)
            layout.addView(detailView)
        }

        return ScrollView(requireContext()).apply { addView(layout) }
    }
}
