package com.teumteum.teumteum.presentation.signup.modal

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.teumteum.teumteum.databinding.ItemSingleModalBinding

class SingleModalAdapter(private val itemClickListener: (String) -> Unit)
    : ListAdapter<String, SingleModalAdapter.ViewHolder>(DiffCallback()) {

    private var selectedItem: String = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSingleModalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, itemClickListener)
    }

    fun setSelectedItem(name: String) {
        selectedItem = name
        notifyDataSetChanged()
    }

    // onBindViewHolder 내부에서 isSelected 메서드를 통해 선택 여부 확인
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem, currentItem.equals(selectedItem))
    }

    class ViewHolder(private val binding: ItemSingleModalBinding, itemClickListener: (String) -> Unit) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = binding.tvItem.text.toString() // 아이템 데이터를 텍스트 뷰에서 가져옴
                    itemClickListener.invoke(item)
                }
            }
        }

        // bind 메서드에서 isSelected 여부에 따라 이미지 처리
        fun bind(item: String, isSelected: Boolean) {
            // Bind your data to the ViewHolder here
            binding.tvItem.text = item

            // 이미지 처리
            if (isSelected) {
                showImage()
            } else {
                hideImage()
            }
        }

        // 이미지를 보이게 하는 메서드
        fun showImage() {
            binding.ivSelected.visibility = View.VISIBLE
        }

        // 이미지를 숨기는 메서드
        fun hideImage() {
            binding.ivSelected.visibility = View.GONE
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }
}

