package com.teumteum.teumteum.presentation.signup.community

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.teumteum.base.BindingFragment
import com.teumteum.base.util.extension.setOnSingleClickListener
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.FragmentCommunityBinding
import com.teumteum.teumteum.presentation.signup.SignUpActivity
import com.teumteum.teumteum.presentation.signup.SignUpViewModel
import com.teumteum.teumteum.presentation.signup.modal.SingleModalBottomSheet
import com.teumteum.teumteum.util.SignupUtils.STATUS_STUDENT
import com.teumteum.teumteum.util.SignupUtils.STATUS_TRAINEE
import com.teumteum.teumteum.util.SignupUtils.STATUS_WORKER
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

        bottomSheet = SingleModalBottomSheet.newInstance(BOTTOM_SHEET_TITLE, communitySort, listener)

        with(binding) {
            llStatus.setOnSingleClickListener {
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
        const val BOTTOM_SHEET_TITLE = "상태 입력"
        val communitySort = arrayListOf(STATUS_WORKER, STATUS_STUDENT, STATUS_TRAINEE)
    }

}