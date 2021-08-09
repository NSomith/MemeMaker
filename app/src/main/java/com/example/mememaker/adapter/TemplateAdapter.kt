package com.example.mememaker.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mememaker.databinding.AdapterTemplatesBinding

class TemplateAdapter(val templates:List<Int>,val listener:OnAdapterTemplateListener):RecyclerView.Adapter<TemplateAdapter.ViewHolder>() {

    inner class ViewHolder( private val binding: AdapterTemplatesBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(template:Int){
            binding.ivTemplate.setBackgroundResource(template)
        }
    }

    interface OnAdapterTemplateListener{
        fun OnAdapterClick(resource:Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            AdapterTemplatesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val template = templates[position]
        holder.bind(template)
        holder.itemView.setOnClickListener {
            listener.OnAdapterClick(template)
        }
    }

    override fun getItemCount(): Int {
        return templates.size
    }
}