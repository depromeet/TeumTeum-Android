package com.teumteum.teumteum.presentation.familiar.topic

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.teumteum.domain.entity.TopicResponse
import com.teumteum.teumteum.databinding.ItemTopicBinding
import com.teumteum.teumteum.util.ResMapper

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
        holder.bind(getItem(position), position)
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

        fun bind(item: TopicResponse, position: Int) {
            val frontImageResId = ResMapper.getFrontImageResId(position)
            val backImageResId = ResMapper.getBackImageResId(position)
            val versusColor = ResMapper.getVersusColor(itemView.context, position)
            val pageNumber = position + 1

            if (item is TopicResponse.Story) {

                with(binding) {
                    tvTopicNumber.text = "TOPIC. $pageNumber"
                    tvTopicTitle.text = item.topic
                    tvStory.isVisible = true
                    tvStory.text = item.story
                    tvVersus.isVisible = false
                    tvBalanceQuestionFirst.isVisible = false
                    tvBalanceQuestionSecond.isVisible = false
                    loadImage(binding.ivFrontBalanceBackground, frontImageResId)
                    loadImage(binding.ivBackBalanceBackground, backImageResId)
                }
            } else if (item is TopicResponse.Balance) {
                with(binding) {
                    tvTopicNumber.text = "TOPIC. $pageNumber"
                    tvTopicTitle.text = item.topic
                    tvVersus.setTextColor(versusColor)
                    tvVersus.isVisible = true
                    tvBalanceQuestionFirst.isVisible = true
                    tvBalanceQuestionSecond.isVisible = true
                    tvBalanceQuestionFirst.text = item.balanceQuestion[0]
                    tvBalanceQuestionSecond.text = item.balanceQuestion[1]

                    loadImage(binding.ivFrontBalanceBackground, frontImageResId)
                    loadImage(binding.ivBackBalanceBackground, backImageResId)
                }
            }
        }

        private fun loadImage(imageView: ImageView, imageResId: Int) {
            Glide.with(itemView.context)
                .load(imageResId)
                .apply(RequestOptions.centerInsideTransform())
                .into(imageView)
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