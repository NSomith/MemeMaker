package com.example.mememaker.adapter

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mememaker.data.InternalStoragePhoto
import com.example.mememaker.databinding.ItemsPhotoBinding

class PhotoStorageAdapter(private val listener: InternalStorageAdapterClick) :
    ListAdapter<InternalStoragePhoto, PhotoStorageAdapter.PhotoViewHolder>(Companion) {

    interface InternalStorageAdapterClick {
        fun onShareClick(fileName: String, bitmap: Bitmap)
        fun onDeleteClick(fileName: String)
    }

    inner class PhotoViewHolder(val binding: ItemsPhotoBinding) :
        RecyclerView.ViewHolder(binding.root)

    companion object : DiffUtil.ItemCallback<InternalStoragePhoto>() {
        override fun areItemsTheSame(
            oldItem: InternalStoragePhoto,
            newItem: InternalStoragePhoto
        ): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(
            oldItem: InternalStoragePhoto,
            newItem: InternalStoragePhoto
        ): Boolean {
            return oldItem.name == newItem.name && oldItem.bmp.sameAs(newItem.bmp)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        return PhotoViewHolder(
            ItemsPhotoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val photo = currentList[position]
        holder.binding.apply {
            ivPhoto.setImageBitmap(photo.bmp)

            val aspectRatio = photo.bmp.width.toFloat() / photo.bmp.height.toFloat()
            ConstraintSet().apply {

                setDimensionRatio(ivPhoto.id, aspectRatio.toString())

            }
            ivDrawShare.setOnClickListener {
                listener.onShareClick(photo.name, photo.bmp)
            }
            ivDrawDelete.setOnClickListener {
                listener.onDeleteClick(photo.name)
            }

        }
    }
}