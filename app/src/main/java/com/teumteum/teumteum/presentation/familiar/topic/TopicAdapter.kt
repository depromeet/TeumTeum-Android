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
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.ItemTopicBinding
import com.teumteum.teumteum.presentation.familiar.topic.model.Topic

class TopicAdapter() :
    ListAdapter<Topic, TopicAdapter.ItemViewHolder>(
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
        fun bind(item: Topic) {
            //front
            with(binding) {
                tvTopicNumber.text = item.topicNumber
                tvTopicTitle.text = item.topicTitle

                Glide.with(itemView.context)
                    .load(item.frontImage)
                    .apply(RequestOptions.centerInsideTransform())
                    .into(binding.ivFrontBalanceBackground)
            }

            //back
            with(binding) {
                Glide.with(itemView.context)
                    .load(item.backImage)
                    .apply(RequestOptions.centerInsideTransform())
                    .into(binding.ivBackBalanceBackground)
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