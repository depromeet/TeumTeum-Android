package com.teumteum.teumteum.presentation.group.join

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.teumteum.base.util.extension.setOnSingleClickListener
import com.teumteum.domain.entity.Friend
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.ItemFriendListBinding
import com.teumteum.teumteum.util.SignupUtils
import com.teumteum.teumteum.util.callback.ItemDiffCallback

class JoinFriendAdapter(private val itemClick: (Friend) -> (Unit)) :
    ListAdapter<Friend, JoinFriendAdapter.JoinFriendViewHolder>(DIFF_UTIL) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JoinFriendViewHolder {
        val binding = ItemFriendListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return JoinFriendViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(holder: JoinFriendViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    class JoinFriendViewHolder(
        private val binding: ItemFriendListBinding,
        private val itemClick: (Friend) -> (Unit)
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: Friend) {
            with(binding.cardFriend) {
                tvJob.text = item.job.detailClass
                tvCompany.text = "@${item.job.name}"
                tvLevel.text = "lv.${item.mannerTemperature}층"
                tvName.text = item.name
                tvArea.text = item.activityArea
                tvMbti.text = item.mbti
                ivCharacter.setImageResource(SignupUtils.CHARACTER_CARD_LIST[item.characterId] ?: R.drawable.ic_card_front_penguin)
            }

            binding.root.setOnSingleClickListener {
                itemClick(item)
            }
        }
    }

    companion object {
        val DIFF_UTIL = ItemDiffCallback<Friend>(
            onItemsTheSame = { old, new -> old.id == new.id },
            onContentsTheSame = { old, new -> old == new },
        )

    }
}