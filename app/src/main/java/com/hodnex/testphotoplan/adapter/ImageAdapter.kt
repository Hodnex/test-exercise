package com.hodnex.testphotoplan.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hodnex.testphotoplan.data.Image
import com.hodnex.testphotoplan.databinding.GridItemImageBinding
import com.squareup.picasso.Picasso

class ImageAdapter(private val listener: OnItemClickListener) :
    ListAdapter<Image, ImageAdapter.ImageViewHolder>(DiffCallback()) {

    var deletable = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding =
            GridItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ImageViewHolder(private val binding: GridItemImageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.apply {
                root.setOnLongClickListener {
                    listener.onItemLongClick()
                    true
                }
                imageUnselected.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val item = getItem(position)
                        listener.onSelectItemClick(item, !imageSelected.isVisible)
                    }
                }
            }
        }

        fun bind(image: Image) {

            binding.apply {
                Picasso.get().load(image.imageUri).into(locationImage)
                if(deletable){
                    if (image.isSelected) {
                        imageSelected.visibility = View.VISIBLE
                        imageUnselected.visibility = View.VISIBLE
                    } else {
                        imageSelected.visibility = View.INVISIBLE
                            imageUnselected.isVisible = true
                        }
                } else {
                    imageSelected.visibility = View.INVISIBLE
                    imageUnselected.visibility = View.INVISIBLE
                }
            }
        }
    }

    interface OnItemClickListener {
        fun onItemLongClick()
        fun onSelectItemClick(image: Image, isSelected: Boolean)
    }

    class DiffCallback : DiffUtil.ItemCallback<Image>() {
        override fun areItemsTheSame(oldItem: Image, newItem: Image) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Image, newItem: Image) =
            oldItem == newItem
    }
}