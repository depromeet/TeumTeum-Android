package com.teumteum.teumteum.presentation.signup.modal

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.teumteum.base.util.extension.setOnSingleClickListener
import com.teumteum.teumteum.databinding.ItemAreaModalStreetBinding

class AreaStreetModalAdapter(private val itemClickListener: (String) -> Unit)
    : ListAdapter<String, AreaStreetModalAdapter.ViewHolder>(DiffCallback()) {

    private var selectedStreet: String = ""
    private var selectedCity: String = ""
    private var focusedCity: String = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemAreaModalStreetBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, itemClickListener)
    }

    /* 선택한 세부 도시 업데이트 */
    fun setSelectedStreet(city: String, street: String) {
        selectedStreet = street
        selectedCity = city
        notifyDataSetChanged()
    }

    /* 현재 탐색 중인 도시명 업데이트 */
    fun setFocusedCity(city: String) {
        focusedCity = city
        notifyDataSetChanged()
    }

    /* onBindViewHolder 내부에서 isSelected 메서드를 통해 선택 여부 확인 */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem, selectedCity.equals(focusedCity) && currentItem.equals(selectedStreet))
    }

    class ViewHolder(private val binding: ItemAreaModalStreetBinding, itemClickListener: (String) -> Unit)
        : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnSingleClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = binding.tvItem.text.toString() // 아이템 데이터를 텍스트 뷰에서 가져옴
                    itemClickListener.invoke(item)
                }
            }
        }

        // bind 메서드에서 isSelected 여부에 따라 이미지 처리
        fun bind(item: String, isSelected: Boolean) {
            binding.tvItem.text = item

            // 이미지 처리
            if (isSelected) {
                showImage()
            } else {
                hideImage()
            }
        }

        private fun showImage() {
            binding.ivSelected.visibility = View.VISIBLE
        }

        private fun hideImage() {
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
