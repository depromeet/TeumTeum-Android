package com.teumteum.teumteum.presentation.familiar.introduce

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.teumteum.teumteum.databinding.ItemIntroduceBinding
import com.teumteum.teumteum.presentation.familiar.introduce.model.Introduce

class IntroduceAdapter() :
    ListAdapter<Introduce, IntroduceAdapter.ItemViewHolder>(
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
        fun bind(item: Introduce) {
            Glide.with(itemView.context)
                .load(item.image)
                .apply(RequestOptions.centerInsideTransform())
                .into(binding.ivIntroduceCard)
        }
    }

    object ItemListDiffCallback : DiffUtil.ItemCallback<Introduce>() {
        override fun areItemsTheSame(oldItem: Introduce, newItem: Introduce): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: Introduce,
            newItem: Introduce
        ): Boolean {
            return oldItem == newItem
        }
    }
}