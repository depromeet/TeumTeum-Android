package com.teumteum.teumteum.presentation.signup.modal

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.teumteum.base.R.*
import com.teumteum.teumteum.databinding.ItemAreaModalCityBinding

class AreaCityModalAdapter(private val itemClickListener: (String) -> Unit)
    : ListAdapter<String, AreaCityModalAdapter.ViewHolder>(AreaCityModalAdapter.DiffCallback()) {

    private var focusedCity: String = ""

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AreaCityModalAdapter.ViewHolder {
        val binding = ItemAreaModalCityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, itemClickListener)
    }

    fun setFocusedCity(name: String) {
        focusedCity = name
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: AreaCityModalAdapter.ViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem, currentItem.equals(focusedCity))
    }

    class ViewHolder(private val binding: ItemAreaModalCityBinding, itemClickListener: (String) -> Unit)
        : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = binding.tvItem.text.toString()
                    itemClickListener.invoke(item)
                }
            }
        }

        fun bind(item: String, isSelected: Boolean) {
            with(binding) {
                tvItem.text = item
                if (isSelected) {
                    root.setBackgroundColor(binding.root.context.getColor(color.elevation_alternative))
                    tvItem.setTextColor(binding.root.context.getColor(color.text_button_primary_default))
                }
                else {
                    root.setBackgroundColor(binding.root.context.getColor(color.transparent))
                    tvItem.setTextColor(binding.root.context.getColor(color.text_body_secondary))
                }
            }
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }
}