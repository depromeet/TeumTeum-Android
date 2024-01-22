package com.teumteum.teumteum.presentation.familiar.onboarding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.teumteum.teumteum.databinding.ItemShakeOnboardingBinding
import com.teumteum.teumteum.presentation.familiar.onboarding.model.FamiliarOnBoarding

class FamiliarOnBoardingAdapter() :
    ListAdapter<FamiliarOnBoarding, FamiliarOnBoardingAdapter.ItemViewHolder>(
        ItemListDiffCallback
    ) {
    private lateinit var binding: ItemShakeOnboardingBinding

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {
        binding =
            ItemShakeOnboardingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ItemViewHolder(private val binding: ItemShakeOnboardingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: FamiliarOnBoarding) {
            with(binding) {
                tvTitle.text = item.title
                tvSubtitle.text = item.subtitle

                Glide.with(itemView.context)
                    .load(item.image)
                    .apply(RequestOptions.centerInsideTransform())
                    .into(binding.ivOnboarding)
            }
        }
    }

    object ItemListDiffCallback : DiffUtil.ItemCallback<FamiliarOnBoarding>() {
        override fun areItemsTheSame(oldItem: FamiliarOnBoarding, newItem: FamiliarOnBoarding): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: FamiliarOnBoarding,
            newItem: FamiliarOnBoarding
        ): Boolean {
            return oldItem.title == newItem.title
        }
    }
}