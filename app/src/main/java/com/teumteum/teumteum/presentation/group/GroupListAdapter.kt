package com.teumteum.teumteum.presentation.group

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.teumteum.base.util.extension.setOnSingleClickListener
import com.teumteum.domain.entity.Meeting
import com.teumteum.teumteum.databinding.ItemGroupListBinding

class GroupListAdapter(private val itemClick: (Meeting) -> (Unit)) :
    RecyclerView.Adapter<GroupListAdapter.GroupListViewHolder>() {
    private val groupList = mutableListOf<Meeting>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupListViewHolder {
        val binding = ItemGroupListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false,
        )
        return GroupListViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(holder: GroupListViewHolder, position: Int) {
        holder.onBind(groupList[position])
    }

    override fun getItemCount(): Int = groupList.size

    fun setItems(newItems: List<Meeting>) {
        groupList.clear()
        groupList.addAll(newItems)
        notifyDataSetChanged()
    }

    fun addItems(newItems: List<Meeting>) {
        groupList.addAll(newItems)
        notifyDataSetChanged()
    }

    class GroupListViewHolder(
        private val binding: ItemGroupListBinding,
        private val itemClick: (Meeting) -> (Unit)
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: Meeting) {
            binding.tvGroupName.text = item.name
            binding.tvTitleBadge.text = item.topic.replace("_", " ")
            binding.tvDate.text = item.date

            binding.ivImage.load(item.photoUrls.first()) {
                transformations(RoundedCornersTransformation(4f))
            }

            binding.root.setOnSingleClickListener {
                itemClick(item)
            }
        }
    }
}