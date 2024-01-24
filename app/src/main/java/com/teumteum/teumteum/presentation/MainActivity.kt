package com.teumteum.teumteum.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.teumteum.base.BindingActivity
import com.teumteum.base.util.extension.intExtra
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.ActivityMainBinding
import com.teumteum.teumteum.presentation.home.HomeFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {
    private val id by intExtra()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fl_main) as NavHostFragment
        val navController = navHostFragment.navController
        binding.btmNavi.setupWithNavController(navController)

        if (id != -1) {
            moveRecommendDetail()
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

    companion object {
        fun getIntent(context: Context, id: Int) = Intent(context, MainActivity::class.java).apply {
            putExtra("id", id)
        }
    }
}
