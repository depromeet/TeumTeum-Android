package com.teumteum.teumteum.presentation.moim

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.teumteum.base.BindingFragment
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.FragmentMoimBinding
import com.teumteum.teumteum.di.NetworkStatus
import com.teumteum.teumteum.presentation.MainActivity
import kotlinx.coroutines.coroutineScope


class MoimFragment :
    BindingFragment<FragmentMoimBinding>(R.layout.fragment_moim) {
    private val viewModel: MoimViewModel by viewModels()

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).hideBottomNavi()
    }

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
                ScreenState.Introduce -> MoimIntroduce(viewModel = viewModel)
                ScreenState.DateTime -> MoimDateTime(viewModel)
                ScreenState.Address -> MoimAddress(viewModel)
                ScreenState.People -> MoimPeople(viewModel)
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

    private fun goFrontScreen() {
        if (viewModel.screenState.value == ScreenState.Topic) {
            findNavController().navigate(R.id.action_moimFragment_to_homeFragment)
            (activity as MainActivity).showBottomNavi()

        } else {
            viewModel.goPreviousScreen()
        }
    }

    private fun animateProgressBar(targetStep: Int) {
        val targetProgress = targetStep * 100 + 100
        ObjectAnimator.ofInt(binding.progressBar, "progress", targetProgress)
            .setDuration(500)
            .start()
    }

    private fun goToWebFragment() {
        val status = NetworkStatus.getConnectivityStatus(requireContext())
        if (status == NetworkStatus.TYPE_MOBILE || status == NetworkStatus.TYPE_WIFI)  {
        } else {
            Toast.makeText(context, "인터넷 연결을 확인해주세요.", Toast.LENGTH_SHORT).show()
        }
    }
    companion object {

    }
}