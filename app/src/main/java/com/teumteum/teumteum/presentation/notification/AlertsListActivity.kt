package com.teumteum.teumteum.presentation.notification

import android.os.Bundle
import com.teumteum.base.BindingActivity
import com.teumteum.base.component.appbar.AppBarLayout
import com.teumteum.base.component.appbar.AppBarMenu
import com.teumteum.base.databinding.LayoutCommonAppbarBinding
import com.teumteum.domain.entity.TeumAlert
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.ActivityAlertsListBinding

class AlertsListActivity
    : BindingActivity<ActivityAlertsListBinding>(R.layout.activity_alerts_list), AppBarLayout {

    private lateinit var adapter: AlertsListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initAppBarLayout()
        initList()
    }

    override val appBarBinding: LayoutCommonAppbarBinding
        get() = binding.appBar

    override fun initAppBarLayout() {
        setAppBarHeight(48)

        addMenuToLeft(
            AppBarMenu.IconStyle(
                resourceId = R.drawable.ic_arrow_left_l,
                useRippleEffect = false,
                clickEvent = ::finish
            )
        )
        setAppBarTitleText(R.string.alerts_list_title)
    }

    private fun initItems() {
        adapter.setItems(listOf(
            TeumAlert("틈 채우기", "한별님이 당신을 추천했어요!", RECOMMEND_USER, "2024-01-30T16:33:00", false),
            TeumAlert("틈 채우기", "한별님이 당신을 추천했어요!", RECOMMEND_USER, "2024-01-31T16:33:00", false),
            TeumAlert("틈 채우기", "한별님이 당신을 추천했어요!", RECOMMEND_USER, "2024-02-10T16:33:00", false),
            TeumAlert("틈 채우기", "한별님이 당신을 추천했어요!", RECOMMEND_USER, "2024-02-11T16:33:00", false),
            TeumAlert("틈 채우기", "한별님이 당신을 추천했어요!", RECOMMEND_USER, "2024-02-12T09:33:00", false),
            TeumAlert("틈 채우기", "한별님이 당신을 추천했어요!", RECOMMEND_USER, "2024-02-12T10:33:00", false)
        ))
    }

    private fun initList() {
        adapter = AlertsListAdapter {
            when (it.type) {
                // 알림 리스트에서 클릭 시 이동하는 정책이 있다면
                BEFORE_MEETING -> {
                    // 틈틈으로 이동
                    // openActivitySlideAnimation()
                }
                END_MEETING -> {
                    // 해당 모임 종료 화면으로 이동
                    // openActivitySlideAnimation()
                }
                RECOMMEND_USER -> {
                    // 마이페이지로 이동
                    // openActivitySlideAnimation()
                }
            }
        }
        initItems()
        binding.rvAlertsList.adapter = adapter
    }

    companion object {
        private const val BEFORE_MEETING = "BEFORE_MEETING"
        private const val END_MEETING = "END_MEETING"
        private const val RECOMMEND_USER = "RECOMMEND_USER"
    }
}