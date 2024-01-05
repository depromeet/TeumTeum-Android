package com.teumteum.teumteum.presentation.onboarding.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.teumteum.domain.entity.ViewPagerEntity
import com.teumteum.teumteum.databinding.ItemOnboardingViewpagerBinding

class OnBoardingViewPagerAdapter: ListAdapter<ViewPagerEntity, OnBoardingViewPagerAdapter.ItemViewHolder>(ItemListDiffCallback) {
    private lateinit var binding: ItemOnboardingViewpagerBinding

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OnBoardingViewPagerAdapter.ItemViewHolder {
        binding = ItemOnboardingViewpagerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OnBoardingViewPagerAdapter.ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ItemViewHolder(private val binding: ItemOnboardingViewpagerBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ViewPagerEntity) {
            binding.tvTitle.text = item.title
            binding.tvSubtitle.text = item.subtitle
//            binding.rectError.setImageResource()
        }
    }

    object ItemListDiffCallback : DiffUtil.ItemCallback<ViewPagerEntity>() {
        override fun areItemsTheSame(oldItem: ViewPagerEntity, newItem: ViewPagerEntity): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: ViewPagerEntity,
            newItem: ViewPagerEntity
        ): Boolean {
            return oldItem.title == newItem.title
        }
    }
}