package com.teumteum.teumteum.util.custom.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.ItemInterestBinding
import com.teumteum.teumteum.util.callback.OnDeletedInterests
import com.teumteum.teumteum.util.custom.view.model.Interest
import timber.log.Timber

class InterestAdapter(val context: Context, val onDeletedInterests: OnDeletedInterests) : ListAdapter<Interest, InterestAdapter.ItemViewHolder>(
    ItemListDiffCallback
) {
    var isModifyDetail: Boolean = false
    var onAddItemClick: (() -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemInterestBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding, isModifyDetail, onAddItemClick) { position ->
            if (currentList.size > 2) {
                removeItem(position)
            } else {
                Toast.makeText(context, "1개 이하는 삭제가 불가능 해요", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    private fun removeItem(position: Int) {
        val newList = currentList.toMutableList().apply {
            removeAt(position)
        }
        onDeletedInterests.deletedInterests(newList)
        submitList(newList)
    }

    class ItemViewHolder(
        private val binding: ItemInterestBinding,
        private val isModifyDetail: Boolean,
        private val onAddItemClick: (() -> Unit)?,
        private val onRemoveItem: (position: Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Interest) {
            with(binding){
                if (item.interest == "추가하기") {
                    tvInterest.text = item.interest
                    tvInterest.setTextColor(ContextCompat.getColor(itemView.context, com.teumteum.base.R.color.text_button_primary_default))
                    clInterest.setBackgroundResource(R.drawable.shape_rect4_color_outline_level01_active)
                    ivDelete.setImageDrawable(ContextCompat.getDrawable(itemView.context,R.drawable.ic_plus_fill))
                    root.setOnClickListener { onAddItemClick?.invoke() }
                } else {
                    tvInterest.text = itemView.context.getString(R.string.item_interest, item.interest)
                    clInterest.setBackgroundResource(R.drawable.shape_rect4_background)
                    root.setOnClickListener(null)
                    ivDelete.setOnClickListener { onRemoveItem(absoluteAdapterPosition) }

                }
                ivDelete.visibility = if (isModifyDetail) View.VISIBLE else View.GONE
            }
        }
    }

    object ItemListDiffCallback : DiffUtil.ItemCallback<Interest>() {
        override fun areItemsTheSame(oldItem: Interest, newItem: Interest): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: Interest, newItem: Interest): Boolean =
            oldItem.interest == newItem.interest
    }
}