package com.teumteum.teumteum.util.custom.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.ItemInterestBinding
import com.teumteum.teumteum.util.custom.view.model.Interest
import timber.log.Timber

class InterestAdapter() : ListAdapter<Interest, InterestAdapter.ItemViewHolder>(
    ItemListDiffCallback
) {
    private lateinit var binding: ItemInterestBinding

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ItemViewHolder {
        binding = ItemInterestBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.binding.root.setOnClickListener {
            removeItem(position)
        }
    }

    private fun removeItem(position: Int) {
        val newList = currentList.toMutableList().apply {
            removeAt(position)
        }
        submitList(newList)
    }

    class ItemViewHolder(val binding: ItemInterestBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Interest) {
            binding.tvInterest.text = itemView.context.getString(R.string.item_interest, item.interest)
        }
    }

    object ItemListDiffCallback : DiffUtil.ItemCallback<Interest>() {
        override fun areItemsTheSame(oldItem: Interest, newItem: Interest): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: Interest, newItem: Interest
        ): Boolean {
            return oldItem.interest == newItem.interest
        }
    }
}