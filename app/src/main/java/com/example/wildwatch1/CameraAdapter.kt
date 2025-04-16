package com.example.wildwatch1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CameraAdapter(
    private val cameraList: MutableList<Camera>,
    private val onRemoveClick: (Camera) -> Unit
) : RecyclerView.Adapter<CameraAdapter.CameraViewHolder>() {

    class CameraViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtRtspLink: TextView = view.findViewById(R.id.txtRtspLink)
        val txtLocation: TextView = view.findViewById(R.id.txtLocation)
        val txtTimestamp: TextView = view.findViewById(R.id.txtTimestamp)
        val btnRemove: Button = view.findViewById(R.id.btnRemoveCamera)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CameraViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_item_camera, parent, false)
        return CameraViewHolder(view)
    }

    override fun onBindViewHolder(holder: CameraViewHolder, position: Int) {
        val camera = cameraList[position]
        holder.txtRtspLink.text = camera.rtspLink
        holder.txtLocation.text = camera.location
        holder.txtTimestamp.text = camera.timestamp

        holder.btnRemove.setOnClickListener {
            onRemoveClick(camera) // Call the function to remove the camera

        }
    }

    override fun getItemCount(): Int = cameraList.size

    // ✅ Function to Add Camera to RecyclerView
    fun addCamera(camera: Camera) {
        cameraList.add(camera)
        notifyItemInserted(cameraList.size - 1)
    }

    // ✅ Function to Remove Camera from RecyclerView
    fun removeCamera(camera: Camera) {
        val position = cameraList.indexOf(camera)
        if (position != -1) {
            cameraList.removeAt(position)
            notifyItemRemoved(position)
        }
    }
}
