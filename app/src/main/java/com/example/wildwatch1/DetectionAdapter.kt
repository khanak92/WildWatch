package com.example.wildwatch1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class DetectionAdapter(private val detections: List<Detection>) :
    RecyclerView.Adapter<DetectionAdapter.DetectionViewHolder>() {

    inner class DetectionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val type = view.findViewById<TextView>(R.id.tvDetectionType)
        val location = view.findViewById<TextView>(R.id.tvLocation)
        val dateTime = view.findViewById<TextView>(R.id.tvDateTime)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetectionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_detection, parent, false)
        return DetectionViewHolder(view)
    }

    override fun onBindViewHolder(holder: DetectionViewHolder, position: Int) {
        val det = detections[position]
        holder.type.text = "Type: ${det.detectionType}"
        holder.location.text = "Location: (${det.latitude}, ${det.longitude})"
        holder.dateTime.text = SimpleDateFormat("MMM dd, yyyy hh:mm a", Locale.getDefault())
            .format(Date(det.timestamp))
    }

    override fun getItemCount(): Int = detections.size
}
