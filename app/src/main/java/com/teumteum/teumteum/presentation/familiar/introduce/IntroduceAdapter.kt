package com.teumteum.teumteum.presentation.familiar.introduce

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.teumteum.domain.entity.Friend
import com.teumteum.teumteum.databinding.ItemIntroduceBinding
import com.teumteum.teumteum.util.IdMapper

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
        fun bind(item: Friend) {
            val imageRes = IdMapper.getCardCharacterDrawableById(characterId = item.characterId)

            with(binding.cvCharacter) {
                tvName.text = item.name
                tvCompany.text = item.job.name
                tvJob.text = item.job.detailClass
                tvLevel.text = item.mannerTemperature.toString()
                tvArea.text = item.activityArea + "에 사는"
                tvMbti.text = item.mbti

                Glide.with(itemView.context)
                    .load(imageRes)
                    .apply(RequestOptions.centerInsideTransform())
                    .into(ivCharacter)
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