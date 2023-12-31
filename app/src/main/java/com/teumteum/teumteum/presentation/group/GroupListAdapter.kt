package com.teumteum.teumteum.presentation.group

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teumteum.domain.entity.Group
import com.teumteum.teumteum.databinding.ItemGroupListBinding

class GroupListAdapter(private val itemClick: (Group) -> (Unit)) :
    RecyclerView.Adapter<GroupListAdapter.GroupListViewHolder>() {
    private val groupList = mutableListOf<Group>()

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

    fun setItems(newItems: List<Group>) {
        groupList.clear()
        groupList.addAll(newItems)
        notifyDataSetChanged()
    }

    class GroupListViewHolder(
        private val binding: ItemGroupListBinding,
        private val itemClick: (Group) -> (Unit)
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: Group) {
            binding.tvGroupName.text = item.name
            binding.tvTitleBadge.text = item.topic
            binding.tvDate.text = item.date

            binding.root.setOnClickListener {
                itemClick(item)
            }
        }
    }
}