package com.example.wildwatch1

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ImageCarouselAdapter(
    private val context: Context,
    private val images: List<Int>,
    private val onItemClick: (Int) -> Unit
) : RecyclerView.Adapter<ImageCarouselAdapter.CarouselViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_carousel_image, parent, false)
        return CarouselViewHolder(view)
    }

    override fun onBindViewHolder(holder: CarouselViewHolder, position: Int) {
        val imageResId = images[position]
        Glide.with(context)    // ✅ Use Glide here
            .load(imageResId)
            .centerCrop()
            .into(holder.imageView)

        holder.imageView.transitionName = "animalImageTransition"
        holder.imageView.tag = "image_$position"

        holder.imageView.setOnClickListener {
            onItemClick(position)
        }
    }

    override fun getItemCount(): Int = images.size

    class CarouselViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
    }
}
