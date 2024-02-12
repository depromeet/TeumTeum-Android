package com.teumteum.teumteum.presentation.notification

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teumteum.base.R.*
import com.teumteum.base.util.extension.setOnSingleClickListener
import com.teumteum.domain.entity.TeumAlert
import com.teumteum.teumteum.databinding.ItemAlertsListBinding
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class AlertsListAdapter(private val itemClick: (TeumAlert) -> (Unit)) :
    RecyclerView.Adapter<AlertsListAdapter.AlertsListViewHolder>() {
    private val alertList = mutableListOf<TeumAlert>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AlertsListViewHolder {
        val binding = ItemAlertsListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return AlertsListViewHolder(binding, parent.context, itemClick)
    }

    override fun onBindViewHolder(holder: AlertsListViewHolder, position: Int) {
        holder.onBind(alertList[position])
    }

    override fun getItemCount(): Int = alertList.size

    fun setItems(newItems: List<TeumAlert>) {
        alertList.clear()
        alertList.addAll(newItems.sortedByDescending {
            LocalDateTime.parse(it.createdAt, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        })
        notifyDataSetChanged()
    }

    class AlertsListViewHolder(
        private val binding: ItemAlertsListBinding,
        private val context: Context,
        private val itemClick: (TeumAlert) -> (Unit)
    ) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: TeumAlert) {
            binding.tvTitle.text = item.title
            binding.tvContent.text = item.body
            binding.tvTime.text = getTimeDifference(item.createdAt)

            if (!item.isRead) {
                binding.root.setBackgroundColor(context.getColor(color.elevation_level01))
            } else {
                binding.root.setBackgroundColor(context.getColor(color.transparent))
            }
            binding.root.setOnSingleClickListener {
                itemClick(item)
            }
        }

        private fun getTimeDifference(inputDateTime: String): String {
            val currentTime = LocalDateTime.now()
            val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
            val inputTime = LocalDateTime.parse(inputDateTime, formatter)

            val diffInMinutes = java.time.Duration.between(inputTime, currentTime).toMinutes()
            val diffInHours = java.time.Duration.between(inputTime, currentTime).toHours()

            return when {
                diffInMinutes < 60 -> "${diffInMinutes}분 전"
                diffInHours < 24 -> "${diffInHours}시간 전"
                else -> "${inputTime.monthValue}월 ${inputTime.dayOfMonth}일"
            }
        }
    }
}