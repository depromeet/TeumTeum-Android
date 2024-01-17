package com.teumteum.teumteum.presentation.moim

import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.teumteum.base.BindingFragment
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.FragmentMoimBinding
import com.teumteum.teumteum.presentation.MainActivity
import kotlinx.coroutines.flow.onEach


class MoimFragment :
    BindingFragment<FragmentMoimBinding>(R.layout.fragment_moim) {
    private val viewModel: MoimViewModel by activityViewModels()

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).hideBottomNavi()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = findNavController()
        observe()

        lifecycleScope.launchWhenStarted {
            viewModel.currentStep.collect {currentStep ->
                animateProgressBar(currentStep)
            }
        }
        binding.composeMoim.setContent {
            val screenState by viewModel.screenState.collectAsState()
            when (screenState) {
                ScreenState.Topic -> MoimCreateTopic(viewModel) { goFrontScreen() }
                ScreenState.Name -> MoimCreateName(viewModel) { goFrontScreen() }
                ScreenState.Introduce -> MoimIntroduce(viewModel) { goFrontScreen()}
                ScreenState.DateTime -> MoimDateTime(viewModel) { goFrontScreen()}
                ScreenState.Address -> MoimAddress(viewModel, navController) { goFrontScreen()}
                ScreenState.People -> MoimPeople(viewModel) { goFrontScreen()}
                ScreenState.Create -> {
                    binding.progressBar.visibility = View.GONE
                    MoimConfirm(viewModel)
                }
                else -> {}
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            goFrontScreen()
        }
    }

    fun goFrontScreen() {
        if (viewModel.screenState.value == ScreenState.Topic) {
            findNavController().navigate(R.id.action_moimFragment_to_homeFragment)
            (activity as MainActivity).showBottomNavi()

        } else {
            viewModel.goPreviousScreen()
        }
    }

    private fun observe() {
        viewModel.screenState.flowWithLifecycle(lifecycle)
            .onEach {
                when(it) {
                    ScreenState.Failure -> {
                        Toast.makeText(context, "알 수 없는 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
                    }
                    ScreenState.Server -> {
                        Toast.makeText(context, "서버 통신에 실패했습니다.", Toast.LENGTH_SHORT).show()
                    }
                    else -> {}
                }
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