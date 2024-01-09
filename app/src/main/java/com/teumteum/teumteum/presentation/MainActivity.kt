package com.teumteum.teumteum.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.commit
import androidx.lifecycle.VIEW_MODEL_STORE_OWNER_KEY
import com.teumteum.base.BindingActivity
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.ActivityMainBinding
import com.teumteum.teumteum.presentation.home.HomeFragment
import com.teumteum.teumteum.presentation.mypage.MyPageFragment
import com.teumteum.teumteum.presentation.teumteum.TeumTeumFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
        setUpListener()
    }

    private fun initView() {
        val initialFragment = HomeFragment()
        supportFragmentManager.commit {
            add(R.id.fl_main, initialFragment)
        }
    }

    private fun setUpListener() {
        binding.btmNavi.setOnItemSelectedListener {
            replaceFragment(it.itemId)
            true
        }
    }

    fun hideBottomNavi() {
        binding.btmNavi.visibility = View.GONE
    }

    fun showBottomNavi() {
        binding.btmNavi.visibility = View.VISIBLE
    }

    private fun replaceFragment(menuItemId: Int) {
        try {
            supportFragmentManager.commit {
                replace(
                    R.id.fl_main, when (menuItemId) {
                        R.id.bottom_menu_home -> HomeFragment()
                        R.id.bottom_menu_teum_teum -> TeumTeumFragment()
                        R.id.bottom_menu_my_page -> MyPageFragment()
                        else -> throw IllegalArgumentException("${this@MainActivity::class.java.simpleName} unkown menuItemId")
                    }
                )
            }
        } catch (e: IllegalArgumentException) {
            supportFragmentManager.commit {
                replace(R.id.fl_main, HomeFragment())
            }
        }
    }

    companion object {}
}