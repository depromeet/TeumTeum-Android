package com.teumteum.teumteum.presentation.moim

import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.teumteum.base.BindingFragment
import com.teumteum.base.component.compose.theme.ColorPalette_Dark
import com.teumteum.base.component.compose.theme.ColorPalette_Light
import com.teumteum.base.component.compose.theme.TmtmColorPalette
import com.teumteum.base.util.extension.defaultToast
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.FragmentMoimBinding
import com.teumteum.teumteum.presentation.MainActivity
import com.teumteum.teumteum.presentation.moim.compose.MoimAddress
import com.teumteum.teumteum.presentation.moim.compose.MoimConfirm
import com.teumteum.teumteum.presentation.moim.compose.MoimCreateName
import com.teumteum.teumteum.presentation.moim.compose.MoimCreateTopic
import com.teumteum.teumteum.presentation.moim.compose.MoimDateTime
import com.teumteum.teumteum.presentation.moim.compose.MoimFinish
import com.teumteum.teumteum.presentation.moim.compose.MoimIntroduce
import com.teumteum.teumteum.presentation.moim.compose.MoimPeople
import kotlinx.coroutines.delay
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

        if (meetingId > 0) {
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
            CompositionLocalProvider(TmtmColorPalette provides if(isSystemInDarkTheme()) ColorPalette_Dark else ColorPalette_Light ) {
                val screenState by viewModel.screenState.collectAsState()
                when (screenState) {
                    ScreenState.Topic -> MoimCreateTopic(viewModel) { goFrontScreen() }
                    ScreenState.Name -> MoimCreateName(viewModel) { goFrontScreen() }
                    ScreenState.Introduce -> MoimIntroduce(viewModel) { goFrontScreen() }
                    ScreenState.DateTime -> MoimDateTime(viewModel) { goFrontScreen() }
                    ScreenState.Address -> MoimAddress(viewModel, navController) { goFrontScreen() }
                    ScreenState.People -> MoimPeople(viewModel) { goFrontScreen() }
                    ScreenState.Create -> {
                        binding.progressBar.visibility = View.GONE
                        viewModel.getUserId()
                        MoimConfirm(viewModel, navController, requireActivity(), false) { goFrontScreen() }
                    }

                    ScreenState.CancelInit, ScreenState.Cancel -> {
                        binding.progressBar.visibility = View.GONE
                        MoimConfirm(
                            viewModel,
                            navController,
                            requireActivity(),
                            true,
                            meetingId
                        ) { navController.popBackStack() }
                    }
                    ScreenState.ReportInit,ScreenState.Report -> {
                        binding.progressBar.visibility = View.GONE
                        MoimConfirm(
                            viewModel,
                            navController,
                            requireActivity(),
                            true,
                            meetingId
                        ) { navController.popBackStack() }
                    }

                    ScreenState.DeleteInit, ScreenState.Delete -> {
                        binding.progressBar.visibility = View.GONE
                        MoimConfirm(
                            viewModel,
                            navController,
                            requireActivity(),
                            true,
                            meetingId
                        ) { navController.popBackStack() }
                    }

                    ScreenState.Success -> {
                        binding.progressBar.visibility = View.GONE
                        MoimFinish(viewModel = viewModel, navController = navController)
                    }

                    else -> {
                        binding.progressBar.visibility = View.GONE
                        MoimConfirm(viewModel, navController, requireActivity(), false)
                    }
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    private fun setupUI() {
        viewModel.updateSheetEvent(ScreenState.Topic)
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
                    ScreenState.Failure -> { context?.defaultToast(getString(R.string.moim_alert_message_failure)) }
                    ScreenState.Server -> { context?.defaultToast(getString(R.string.moim_alert_message_server)) }
                    ScreenState.Create -> {
                        viewModel.getUserId()
                    }
                    ScreenState.CancelSuccess -> {
                        context?.defaultToast(getString(R.string.moim_alert_message_success))
                        val navController = findNavController()
                        navController.popBackStack()
                        delay(1000)
                        viewModel.initializeState()
                    }
                    ScreenState.Success -> {
                        delay(1000)
                        (activity as MainActivity).showBottomNavi()
                        delay(5000)
                        viewModel.initializeState()
                    }
                    ScreenState.DeleteSuccess -> {
                        context?.defaultToast("모임 삭제를 성공했습니다")
                        val navController = findNavController()
                        navController.popBackStack()
                        delay(2000)
                        viewModel.initializeState()
                    }
                    ScreenState.Modify -> {
                        val navController = findNavController()
                        navController.navigate(R.id.action_fragment_moim_to_fragment_modify_moim)
                    }
                    ScreenState.ReportSuccess -> {
                        context?.defaultToast("모임 신고를 완료했습니다")
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