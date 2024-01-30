package com.teumteum.teumteum.presentation

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.teumteum.base.BindingActivity
import com.teumteum.base.util.extension.boolExtra
import com.teumteum.base.util.extension.intExtra
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.ActivityMainBinding
import com.teumteum.teumteum.presentation.home.HomeFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {
    private val id by intExtra()
    private val isFromAlarm by boolExtra()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestNotificationPermission()

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fl_main) as NavHostFragment
        val navController = navHostFragment.navController
        binding.btmNavi.setupWithNavController(navController)

        if (id != -1) {
            moveRecommendDetail()
        }

        if (isFromAlarm) {
            val action = HomeFragmentDirections.actionHomeFragmentToFragmentFamiliar()
            val navHostFragment = supportFragmentManager.findFragmentById(R.id.fl_main) as NavHostFragment
            navHostFragment.navController.navigate(action)
        }
    }

    fun hideBottomNavi() {
        binding.btmNavi.visibility = View.GONE
    }

    fun showBottomNavi() {
        binding.btmNavi.visibility = View.VISIBLE
    }

    private fun moveRecommendDetail() {
        val action =
            HomeFragmentDirections.actionFragmentHomeToFragmentRecommendDetail(
                id, true
            )
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fl_main) as NavHostFragment
        navHostFragment.navController.navigate(action)
    }

    private val requestNotificationPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { ok ->
            if (ok) {
                // 알림권한 허용 o
            } else {
                // 알림권한 허용 x. 자유롭게 대응..
            }
        }

    fun requestNotificationPermission() {
        if (
            ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                // 다른 런타임 퍼미션이랑 비슷한 과정
                if (shouldShowRequestPermissionRationale(android.Manifest.permission.POST_NOTIFICATIONS)) {
                    // 왜 알림을 허용해야하는지 유저에게 알려주기를 권장
                } else {
                    requestNotificationPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
                }
            } else {
                // 안드로이드 12 이하는 알림에 런타임 퍼미션 없으니, 설정가서 켜보라고 권해볼 수 있겠다.
            }
        }
    }

    override fun finish() {
        super.finish()
        closeActivitySlideAnimation()
    }

    companion object {
        fun getIntent(context: Context, id: Int, isFromAlarm: Boolean = false) = Intent(context, MainActivity::class.java).apply {
            putExtra("id", id)
            putExtra("isFromAlarm", isFromAlarm)
        }
    }
}
