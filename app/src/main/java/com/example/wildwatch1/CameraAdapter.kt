package com.example.wildwatch1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CameraAdapter(
    private var cameraList: MutableList<Camera>,
    private val onRemoveClick: (Camera) -> Unit
) : RecyclerView.Adapter<CameraAdapter.CameraViewHolder>() {

    inner class CameraViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtCameraName: TextView = itemView.findViewById(R.id.txtCameraName)
        val txtRtspLink: TextView = itemView.findViewById(R.id.txtRtspLink)
        val btnRemove: Button = itemView.findViewById(R.id.btnRemove)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CameraViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_item_camera, parent, false)
        return CameraViewHolder(view)
    }

    override fun onBindViewHolder(holder: CameraViewHolder, position: Int) {
        val camera = cameraList[position]
        holder.txtCameraName.text = camera.location
        holder.txtRtspLink.text = camera.rtspLink
        holder.btnRemove.setOnClickListener { onRemoveClick(camera) }
    }
    fun removeCamera(camera: Camera) {
        val position = cameraList.indexOf(camera)
        if (position != -1) {
            cameraList.removeAt(position)
            notifyItemRemoved(position)
        }
    }


    override fun getItemCount(): Int = cameraList.size

    fun addCamera(camera: Camera) {
        cameraList.add(camera)
        notifyItemInserted(cameraList.size - 1)
    }
}
