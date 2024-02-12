package com.teumteum.teumteum.presentation.group.review

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.teumteum.base.util.extension.setOnSingleClickListener
import com.teumteum.domain.entity.ReviewFriend
import com.teumteum.teumteum.databinding.ItemReviewUserBinding
import com.teumteum.teumteum.util.ResMapper
import com.teumteum.teumteum.util.callback.ItemDiffCallback

class ReviewFriendListAdapter(private val itemClick: (ReviewFriend) -> (Unit)) :
    ListAdapter<ReviewFriend, ReviewFriendListAdapter.ReviewFriendViewHolder>(
        DIFF_UTIL
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewFriendViewHolder {
        val binding = ItemReviewUserBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ReviewFriendViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(holder: ReviewFriendViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    class ReviewFriendViewHolder(
        private val binding: ItemReviewUserBinding,
        private val itemClick: (ReviewFriend) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: ReviewFriend) {
            binding.tvName.text = item.name
            binding.tvCompany.text = item.job
            binding.ivSelected.isSelected = item.isSelected
            binding.ivUserIcon.setImageResource(ResMapper.getCharacterDrawableById(item.characterId.toInt()))

            binding.root.setOnSingleClickListener {
                itemClick(item)
            }
        }
    }

    companion object {
        val DIFF_UTIL = ItemDiffCallback<ReviewFriend>(
            onItemsTheSame = { old, new -> old.id == new.id },
            onContentsTheSame = { old, new -> old.isSelected == new.isSelected },
        )

    }
}