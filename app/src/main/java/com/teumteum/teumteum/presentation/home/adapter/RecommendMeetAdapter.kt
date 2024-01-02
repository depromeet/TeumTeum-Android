package com.teumteum.teumteum.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.teumteum.domain.entity.RecommendMeetEntity
import com.teumteum.teumteum.databinding.ItemRecommendMeetBinding
import com.teumteum.teumteum.util.callback.ItemDiffCallback

class RecommendMeetAdapter(
) : ListAdapter<RecommendMeetEntity, RecommendMeetAdapter.RecommendMeetItemViewHolder>(diffUtil) {
    inner class RecommendMeetItemViewHolder(
        private val binding: ItemRecommendMeetBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: RecommendMeetEntity) {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendMeetItemViewHolder {
        val view =
            ItemRecommendMeetBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecommendMeetItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecommendMeetItemViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        private val diffUtil = ItemDiffCallback<RecommendMeetEntity>(
            onItemsTheSame = { old, new -> old == new },
            onContentsTheSame = { old, new -> old == new }
        )
    }
}
