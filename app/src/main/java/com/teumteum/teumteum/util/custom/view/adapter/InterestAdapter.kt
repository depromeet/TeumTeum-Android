package com.teumteum.teumteum.util.custom.view.adapter

import android.view.LayoutInflater
import android.view.View
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
    var isModifyDetail: Boolean = false // 외부에서 접근 가능하도록 isModifyDetail 속성 추가

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemInterestBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        // onCreateViewHolder 시점에 isModifyDetail 값을 ItemViewHolder에 전달
        return ItemViewHolder(binding, isModifyDetail)
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

    class ItemViewHolder(val binding: ItemInterestBinding, private val isModifyDetail: Boolean) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Interest) {
            binding.tvInterest.text = itemView.context.getString(R.string.item_interest, item.interest)
            // 생성자를 통해 전달받은 isModifyDetail 값을 사용
            binding.ivDelete.visibility = if (isModifyDetail) View.VISIBLE else View.GONE
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