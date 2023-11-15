package com.loder.fetchhome.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.loder.fetchhome.data.model.ItemModel
import com.loder.fetchhome.databinding.ItemLayoutBinding

class ItemAdapter(private val itemList: List<ItemModel>) : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemName = itemList.get(position).name
        if (itemName != null) {
            if (!itemName.equals("") || !itemName.equals("null")) {
                holder.binding.textName.text = itemList.get(position).name
                holder.binding.textId.text = itemList.get(position).id.toString()
            }
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class ViewHolder(val binding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)
}
