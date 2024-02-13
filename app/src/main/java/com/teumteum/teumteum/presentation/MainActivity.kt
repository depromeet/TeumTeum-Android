package com.teumteum.teumteum.presentation

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.teumteum.base.BindingActivity
import com.teumteum.base.util.extension.boolExtra
import com.teumteum.base.util.extension.intExtra
import com.teumteum.base.util.extension.longExtra
import com.teumteum.base.util.extension.stringExtra
import com.teumteum.domain.entity.Message
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.ActivityMainBinding
import com.teumteum.teumteum.presentation.group.review.ReviewOnboardingActivity
import com.teumteum.teumteum.presentation.home.HomeFragmentDirections
import com.teumteum.teumteum.presentation.signin.SignInViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {
    private val id by intExtra()
    private var isGroup: Boolean = false
    private val isFromAlarm by boolExtra()
    private val meetingId by longExtra()
    private val title by stringExtra()

    private val viewModel by viewModels<SignInViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        checkAskedNotification()

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fl_main) as NavHostFragment
        val navController = navHostFragment.navController
        binding.btmNavi.setupWithNavController(navController)
        isGroup = intent.getBooleanExtra("isGroup", false)

        if (id != -1) { moveRecommendDetail() }
        if(isGroup) { moveWebView() }
        if (meetingId != -1L && title != null) {
            moveReviewOnboardingActivity()
        }

        if (isFromAlarm) {
            val message = intent.getSerializableExtra(MESSAGE) as Message
            var action = HomeFragmentDirections.actionHomeFragmentToFragmentFamiliar()
            when (message.type) {
                BEFORE_MEETING -> {
                }
                END_MEETING -> {
//                    val meetingId = message.meetingId
//                    val participants = message.participants
//                    action = HomeFragmentDirections.{홈 -> 유저리뷰로 이동하는 navi}
                }
                RECOMMEND_USER -> {
                    action = HomeFragmentDirections.actionHomeFragmentToFragmentMyPage()
                }
            }
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

    private fun moveReviewOnboardingActivity() {
        startActivity(ReviewOnboardingActivity.getIntent(this, meetingId, title!!))
    }

    fun returnGroupDetail(fullAddress:String) {
        val returnIntent = Intent().apply {
            putExtra("address", fullAddress)
        }
        setResult(Activity.RESULT_OK, returnIntent)
        finish()
    }

    private fun moveWebView() {
        val action =
            HomeFragmentDirections.actionFragmentHomeToFragmentWebView(isGroup)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fl_main) as NavHostFragment
        navHostFragment.navController.navigate(action)
    }

    private fun checkAskedNotification() {
        if (!viewModel.getAskedNotification()) {
            requestNotificationPermission()
            viewModel.setAskedkNotification(true)
        }
    }

    private val requestNotificationPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { _ ->
        }

    private fun requestNotificationPermission() {
        if (
            ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (shouldShowRequestPermissionRationale(android.Manifest.permission.POST_NOTIFICATIONS)) {
                } else {
                    requestNotificationPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
                }
            }
        }
    }

    override fun finish() {
        super.finish()
        closeActivitySlideAnimation()
    }

    override fun onResume() {
        super.onResume()
        navigateToHomeFragmentIfNeeded()
    }

    // 다른 Activity에서 FamiliarFragment로 되돌아올 때 HomeFragment로 이동하게 임시 조치 해놓음
    private fun navigateToHomeFragmentIfNeeded() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fl_main) as? NavHostFragment
        val navController = navHostFragment?.navController

        // 현재 활성화된 Fragment가 FamiliarFragment인지 확인
        if (navController?.currentDestination?.id == R.id.fragment_familiar) {
            navController.navigate(R.id.action_global_homeFragment)
        }
    }

    companion object {
        fun getIntent(context: Context, id: Int) = Intent(context, MainActivity::class.java).apply {
            putExtra("id", id)
        }
        fun getIntent(context: Context, id: Int, isFromAlarm: Boolean = false) = Intent(context, MainActivity::class.java).apply {
            putExtra("id", id)
            putExtra(IS_FROM_ALARM, isFromAlarm)
        }
        fun getIntent(context: Context, meetingId: Long, title: String) = Intent(context, MainActivity::class.java).apply {
            putExtra("meetingId", meetingId)
            putExtra("title", title)
        }
        private const val IS_FROM_ALARM = "isFromAlarm"
        private const val MESSAGE = "message"

        private const val BEFORE_MEETING = "BEFORE_MEETING"
        private const val END_MEETING = "END_MEETING"
        private const val RECOMMEND_USER = "RECOMMEND_USER"
    }
}
