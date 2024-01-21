package com.teumteum.teumteum.util.custom.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.teumteum.teumteum.databinding.ItemTopicBinding
import com.teumteum.teumteum.presentation.teumteum.topic.model.Topic

class InterestsAdapter() :
    ListAdapter<Topic, InterestsAdapter.ItemViewHolder>(
        ItemListDiffCallback
    ) {
    private lateinit var binding: ItemTopicBinding

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {
        binding =
            ItemTopicBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ItemViewHolder(private val binding: ItemTopicBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Topic) {
            with(binding) {
                tvTopicNumber.text = item.topicNumber
                tvTopicTitle.text = item.topicTitle

                Glide.with(itemView.context)
                    .load(item.image)
                    .apply(RequestOptions.centerInsideTransform())
                    .into(binding.ivBalanceBackground)
            }
        }
    }

    object ItemListDiffCallback : DiffUtil.ItemCallback<Topic>() {
        override fun areItemsTheSame(oldItem: Topic, newItem: Topic): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: Topic,
            newItem: Topic
        ): Boolean {
            return oldItem.topicNumber == newItem.topicNumber
        }
    }
}