package com.teumteum.teumteum.presentation.familiar.introduce

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
import com.teumteum.domain.entity.Friend
import com.teumteum.teumteum.databinding.ItemIntroduceBinding
import com.teumteum.teumteum.util.ResMapper

class IntroduceAdapter() :
    ListAdapter<Friend, IntroduceAdapter.ItemViewHolder>(
        ItemListDiffCallback
    ) {
    private lateinit var binding: ItemIntroduceBinding

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {
        binding =
            ItemIntroduceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ItemViewHolder(private val binding: ItemIntroduceBinding) :
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
            binding.cardviewFront.cameraDistance = 8000 * scale
            binding.cardviewBack.cameraDistance = 8000 * scale

            frontAnimation = AnimatorInflater.loadAnimator(
                itemView.context,
                com.teumteum.base.R.anim.card_reverse_front
            ) as AnimatorSet
            backAnimation = AnimatorInflater.loadAnimator(
                itemView.context,
                com.teumteum.base.R.anim.card_reverse_back
            ) as AnimatorSet

            binding.cardviewFront.setOnClickListener {
                startAnim()
            }
            binding.cardviewBack.setOnClickListener {
                startAnim()
            }
        }

        private fun startAnim() {
            if (isFront) {
                frontAnimation.setTarget(binding.cardviewFront)
                backAnimation.setTarget(binding.cardviewBack)
                frontAnimation.start()
                backAnimation.start()
                isFront = false
            } else {
                frontAnimation.setTarget(binding.cardviewBack)
                backAnimation.setTarget(binding.cardviewFront)
                backAnimation.start()
                frontAnimation.start()
                isFront = true
            }
        }

        fun bind(item: Friend) {
            val frontImageRes = ResMapper.getFrontCardCharacterDrawableById(characterId = item.characterId)
            val backImageRes = ResMapper.getBackCardCharacterDrawableById(characterId = item.characterId)

            with(binding.cardviewFront) {
                tvName.text = item.name
                tvCompany.text = "@" + item.job.name
                tvJob.text = item.job.detailClass
                tvLevel.text = "lv." + item.mannerTemperature.toString()
                tvArea.text = item.activityArea + "에 사는"
                tvMbti.text = item.mbti

                Glide.with(itemView.context)
                    .load(frontImageRes)
                    .apply(RequestOptions.centerInsideTransform())
                    .into(ivCharacter)
            }

            with(binding.cardviewBack) {
                tvGoalTitle.text = "GOAL"
                tvGoalContent.text = item.goal

                Glide.with(itemView.context)
                    .load(backImageRes)
                    .apply(RequestOptions.centerInsideTransform())
                    .into(ivCharacter)

                isModify = false
                isModifyDetail = false
            }
        }
    }

    object ItemListDiffCallback : DiffUtil.ItemCallback<Friend>() {
        override fun areItemsTheSame(oldItem: Friend, newItem: Friend): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Friend,
            newItem: Friend
        ): Boolean {
            return oldItem == newItem
        }
    }
}