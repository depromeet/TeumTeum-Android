package com.teumteum.teumteum.presentation.group

import android.os.Bundle
import android.widget.Toast
import com.teumteum.base.BindingActivity
import com.teumteum.domain.entity.Group
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.ActivityGroupListBinding

class GroupListActivity : BindingActivity<ActivityGroupListBinding>(R.layout.activity_group_list) {
    private lateinit var adapter: GroupListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
    }

    private fun initView() {
        binding.tvTitle.text = "모각작"

        adapter = GroupListAdapter {
            Toast.makeText(this, it.name, Toast.LENGTH_SHORT).show()
        }

        binding.rvGroupList.adapter = adapter
        adapter.setItems(
            listOf(
                Group(1L, "모각작", "모여서 작업할 사람", "나는 소개", listOf("", ""), "1월 10일 오후 07:00"),
                Group(2L, "모각작", "모여서 작업 안할 사람", "나는 소개", listOf("", ""), "1월 21일 오후 04:00"),
                Group(3L, "모각작", "모여서 작업 하기 싫은 사람", "나는 소개", listOf("", ""), "1월 26일 오후 11:00"),
                Group(4L, "모각작", "모여서 작업 하고 싶은 사람", "나는 소개", listOf("", ""), "2월 13일 오전 11:00"),
                Group(5L, "모각작", "모일 사람", "나는 소개", listOf("", ""), "4월 06일 오후 04:00"),
                Group(6L, "모각작", "안 모일 사람", "나는 소개", listOf("", ""), "11월 16일 오후 08:00"),
            )
        )
    }
}