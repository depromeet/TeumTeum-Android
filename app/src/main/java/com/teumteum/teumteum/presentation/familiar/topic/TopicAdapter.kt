package com.teumteum.teumteum.presentation.familiar.topic

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.teumteum.domain.entity.TopicResponse
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.ItemTopicBinding

class TopicAdapter :
    ListAdapter<TopicResponse, TopicAdapter.ItemViewHolder>(ItemListDiffCallback) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {
        val binding = ItemTopicBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ItemViewHolder(private val binding: ItemTopicBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private lateinit var frontAnimation: AnimatorSet
        private lateinit var backAnimation: AnimatorSet
        private var isFront = true

        init {
            initCardAnim()
        }

        @SuppressLint("ResourceType")
        private fun initCardAnim() {
            val scale = itemView.resources.displayMetrics.density
            binding.clFrontTopic.cameraDistance = 8000 * scale
            binding.clBackTopic.cameraDistance = 8000 * scale

            frontAnimation = AnimatorInflater.loadAnimator(
                itemView.context,
                com.teumteum.base.R.anim.card_reverse_front
            ) as AnimatorSet
            backAnimation = AnimatorInflater.loadAnimator(
                itemView.context,
                com.teumteum.base.R.anim.card_reverse_back
            ) as AnimatorSet

            binding.clFrontTopic.setOnClickListener {
                startAnim()
            }
            binding.clBackTopic.setOnClickListener {
                startAnim()
            }
        }

        private fun startAnim() {
            if (isFront) {
                frontAnimation.setTarget(binding.clFrontTopic)
                backAnimation.setTarget(binding.clBackTopic)
                frontAnimation.start()
                backAnimation.start()
                isFront = false
            } else {
                frontAnimation.setTarget(binding.clBackTopic)
                backAnimation.setTarget(binding.clFrontTopic)
                backAnimation.start()
                frontAnimation.start()
                isFront = true
            }
        }

        fun bind(item: TopicResponse) {
            if (item is TopicResponse.Story) {
                // Handle Story data
                with(binding) {
                    tvTopicNumber.text = "1"
                    tvTopicTitle.text = item.topic

                    Glide.with(itemView.context)
                        .load(R.drawable.ic_front_balance_background_1)
                        .apply(RequestOptions.centerInsideTransform())
                        .into(binding.ivFrontBalanceBackground)

                    Glide.with(itemView.context)
                        .load(R.drawable.ic_back_balance_background_1)
                        .apply(RequestOptions.centerInsideTransform())
                        .into(binding.ivBackBalanceBackground)
                }
            } else if (item is TopicResponse.Balance) {
                // Handle Balance data
                // You can similarly bind Balance data to your views here
            }
        }
    }

    object ItemListDiffCallback : DiffUtil.ItemCallback<TopicResponse>() {
        override fun areItemsTheSame(oldItem: TopicResponse, newItem: TopicResponse): Boolean {
            return when {
                oldItem is TopicResponse.Balance && newItem is TopicResponse.Balance ->
                    oldItem.topic == newItem.topic // Compare based on unique identifiers
                oldItem is TopicResponse.Story && newItem is TopicResponse.Story ->
                    oldItem.topic == newItem.topic // Compare based on unique identifiers
                else -> false
            }
        }

        override fun areContentsTheSame(oldItem: TopicResponse, newItem: TopicResponse): Boolean {
            return oldItem == newItem // Data classes automatically implement equals for all properties
        }
    }
}