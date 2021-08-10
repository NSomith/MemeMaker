package com.example.mememaker.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.mememaker.R
import com.example.mememaker.databinding.ItemColorPalletBinding

class ColorPallet (private val colorResList:List<Int>,
private val listener:OnNoteColorAdapterClick
):RecyclerView.Adapter<ColorPallet.ViewHolder>() {

    interface OnNoteColorAdapterClick{
        fun onClick(colorRes:Int)
    }

    inner class ViewHolder(val binding: ItemColorPalletBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(colorResource:Int){
            val drawable= ResourcesCompat.getDrawable(binding.root.resources, R.drawable.circle,null)
            drawable?.let {
                val wrappedDrawable = DrawableCompat.wrap(it)
                DrawableCompat.setTint(wrappedDrawable, colorResource)
                binding.viewAdapterNoteColor.background=wrappedDrawable
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding=ItemColorPalletBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val colorRes=colorResList[position]
        holder.bind(colorRes)
        holder.binding.root.setOnClickListener {

            listener.onClick(colorRes)

        }

    }

    override fun getItemCount(): Int {
        return colorResList.size
    }
}