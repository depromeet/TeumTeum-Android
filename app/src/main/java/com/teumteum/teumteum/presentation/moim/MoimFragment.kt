package com.teumteum.teumteum.presentation.moim

import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.teumteum.base.BindingFragment
import com.teumteum.base.util.extension.defaultToast
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.FragmentMoimBinding
import com.teumteum.teumteum.presentation.MainActivity
import kotlinx.coroutines.flow.launchIn
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

        observe()

        val navController = findNavController()
        val meetingId = arguments?.getLong("meetingId", -1L) ?: -1L

        if (meetingId >= 0) {
            Log.d("meetingId", meetingId.toString())
            // meetingId가 있는 경우의 로직
            viewModel.getGroup(meetingId)
            viewModel.updateSheetEvent(ScreenState.CancelInit)
        } else {
            setupUI()
        }

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
                        viewModel.getUserId()
                        MoimConfirm(viewModel, requireActivity(),false) { goFrontScreen()}
                    }
                    ScreenState.CancelInit, ScreenState.Cancel -> {
                        binding.progressBar.visibility = View.GONE
                        MoimConfirm(viewModel, requireActivity(),true, meetingId) {navController.popBackStack()}
                    }
                    ScreenState.Success -> MoimFinish(viewModel = viewModel, onClick = {goFrontScreen()} ,navController = navController)

                    else -> {
                        binding.progressBar.visibility = View.GONE
                        MoimConfirm(viewModel, requireActivity(),false)
                    }
                }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    private fun setupUI() {

        lifecycleScope.launchWhenStarted {
            viewModel.currentStep.collect {currentStep ->
                animateProgressBar(currentStep)
            }
        }

    }

    val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            goFrontScreen()
        }
    }

    fun goFrontScreen() {
        if (viewModel.screenState.value == ScreenState.Topic) {
            findNavController().navigate(R.id.action_fragment_moim_to_fragment_home)
            (activity as MainActivity).showBottomNavi()
            viewModel.initializeState()

        } else {
            viewModel.goPreviousScreen()
        }
    }

    private fun observe() {
        viewModel.screenState.flowWithLifecycle(lifecycle)
            .onEach {
                when(it) {
                    ScreenState.Failure -> { context?.defaultToast("모임 신청에 오류가 발생했습니다") }
                    ScreenState.Server -> { context?.defaultToast("서버 통신에 실패했습니다") }
                    ScreenState.Create -> {
                        viewModel.getUserId()
                    }
                    ScreenState.CancelSuccess -> {
                        context?.defaultToast("모임 취소를 완료했습니다")
                        val navController = findNavController()
                        navController.popBackStack()
                        viewModel.initializeState()
                    }
                    else -> {}
                }
            }
            .launchIn(lifecycleScope)
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