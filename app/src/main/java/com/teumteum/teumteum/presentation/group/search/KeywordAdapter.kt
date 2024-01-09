package com.teumteum.teumteum.presentation.group.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teumteum.teumteum.databinding.ItemKeywordListBinding

class keywordAdapter(private val itemClick: (String) -> (Unit)): RecyclerView.Adapter<keywordAdapter.KeywordAdapter>() {
    private val keywordList = mutableListOf<String>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KeywordAdapter {
        val binding = ItemKeywordListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false,
        )
        return KeywordAdapter(binding, itemClick)
    }

    override fun onBindViewHolder(holder: KeywordAdapter, position: Int) {
        holder.onBind(keywordList[position])
    }

    override fun getItemCount(): Int = keywordList.size

    fun setItems(newItems: List<String>) {
        keywordList.clear()
        keywordList.addAll(newItems)
        notifyDataSetChanged()
    }

    class KeywordAdapter(
        private val binding: ItemKeywordListBinding,
        private val itemClick: (String) -> (Unit)
    ): RecyclerView.ViewHolder(binding.root) {
        fun onBind(keyword: String) {
            binding.tvKeyword.text = keyword

            binding.root.setOnClickListener {
                itemClick(keyword)
            }
        }
    }
}