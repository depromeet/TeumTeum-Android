package com.teumteum.teumteum.presentation.signup.community

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.teumteum.base.BindingFragment
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.FragmentCommunityBinding
import com.teumteum.teumteum.presentation.signup.SignUpActivity
import com.teumteum.teumteum.presentation.signup.SignUpViewModel
import com.teumteum.teumteum.presentation.signup.job.CurrentJobFragment.Companion.JOB_PLANNING
import com.teumteum.teumteum.presentation.signup.modal.SingleModalBottomSheet
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CommunityFragment
    : BindingFragment<FragmentCommunityBinding>(R.layout.fragment_community) {

    private val viewModel by activityViewModels<SignUpViewModel>()
    private var bottomSheet: SingleModalBottomSheet? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        binding.lifecycleOwner = this
        initBottomSheet()
        checkValidInput()
    }

    private fun initBottomSheet() {
        val listener: (String) -> Unit = { item ->
            viewModel.updateCommunity(item)
            bottomSheet?.dismiss()
        }

        bottomSheet = SingleModalBottomSheet.newInstance("상태 입력", communitySort, listener)

        with(binding) {
            llStatus.setOnClickListener {
                bottomSheet?.setFocusedImageView(ivShow)
                bottomSheet?.setSelectedItem(viewModel.community.value)
                bottomSheet?.show(childFragmentManager, SingleModalBottomSheet.TAG)
                ivShow.setImageResource(R.drawable.ic_arrow_up_l)
            }
        }
    }

    private fun checkValidInput() {
        lifecycleScope.launch {
            viewModel.community.collect { community ->
                if (community in communitySort) (activity as SignUpActivity).activateNextButton()
                else (activity as SignUpActivity).disableNextButton()
            }
        }
    }

    companion object {
        private const val COMMUNITY_WORKER = "직장인"
        private const val COMMUNITY_STUDENT = "학생"
        private const val COMMUNITY_TRAINEE = "취업준비생"

        val communitySort = arrayListOf(COMMUNITY_WORKER, COMMUNITY_STUDENT, COMMUNITY_TRAINEE)
    }

}