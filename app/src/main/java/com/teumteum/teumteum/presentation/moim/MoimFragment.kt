package com.teumteum.teumteum.presentation.moim

import android.animation.ObjectAnimator
import android.database.Observable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.teumteum.base.BindingFragment
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.FragmentMoimBinding
import java.util.concurrent.TimeUnit


class MoimFragment :
    BindingFragment<FragmentMoimBinding>(R.layout.fragment_moim) {
    private val viewModel: MoimViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launchWhenStarted {
            viewModel.currentStep.collect {currentStep ->
                animateProgressBar(currentStep)
            }
        }

        binding.composeMoim.setContent {
            val screenState by viewModel.screenState.collectAsState()
            when (screenState) {
                ScreenState.Topic -> MoimCreateTopic(viewModel)
                ScreenState.Name -> MoimCreateName(viewModel)
                ScreenState.Introduce -> MoimIntroduce(viewModel)
                ScreenState.DateTime -> MoimDateTime()
                ScreenState.Address -> MoimAddress()
                else -> {}
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            viewModel.goPreviousScreen()
        }
    }

    private fun animateProgressBar(targetStep: Int) {
        val targetProgress = targetStep * 100 + 100
        ObjectAnimator.ofInt(binding.progressBar, "progress", targetProgress)
            .setDuration(500)
            .start()
    }
    companion object {

    }
}