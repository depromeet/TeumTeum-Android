package com.teumteum.teumteum.presentation

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.teumteum.base.BindingActivity
import com.teumteum.base.util.extension.boolExtra
import com.teumteum.base.util.extension.intExtra
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.ActivityMainBinding
import com.teumteum.teumteum.presentation.home.HomeFragmentDirections
import com.teumteum.teumteum.presentation.signin.SignInViewModel
import com.teumteum.teumteum.presentation.splash.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {
    private val id by intExtra()
    private val isFromAlarm by boolExtra()

    private val viewModel by viewModels<SignInViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        checkAskedNotification()

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

    companion object {
        fun getIntent(context: Context, id: Int, isFromAlarm: Boolean = false) = Intent(context, MainActivity::class.java).apply {
            putExtra("id", id)
            putExtra("isFromAlarm", isFromAlarm)
        }
    }
}
