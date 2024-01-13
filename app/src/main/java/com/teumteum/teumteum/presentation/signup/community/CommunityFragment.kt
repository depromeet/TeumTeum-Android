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
//import com.teumteum.teumteum.presentation.signup.modal.SingleModalBottomSheet
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CommunityFragment
    : BindingFragment<FragmentCommunityBinding>(R.layout.fragment_community) {

    private val viewModel by activityViewModels<SignUpViewModel>()
//    private var bottomSheet: SingleModalBottomSheet? = null
    private val communityList = ArrayList<String>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        binding.lifecycleOwner = this
        initBottomSheet()
        checkValidInput()
    }

    private fun initBottomSheet() {
        communityList.addAll(arrayOf("직장인", "학생", "취업준비생"))

        val listener: (String) -> Unit = { item ->
            viewModel.updateCommunity(item)
//            bottomSheet?.dismiss()
        }

//        bottomSheet = SingleModalBottomSheet.newInstance("상태 입력", communityList, listener)

        with(binding) {
            llStatus.setOnClickListener {
//                bottomSheet?.setFocusedImageView(ivShow)
//                bottomSheet?.show(childFragmentManager, SingleModalBottomSheet.TAG)
                ivShow.setImageResource(R.drawable.ic_arrow_up_l)
            }
        }
    }

    private fun checkValidInput() {
        lifecycleScope.launch {
            viewModel.community.collect { community ->
                if (community in communityList) (activity as SignUpActivity).activateNextButton()
                else (activity as SignUpActivity).disableNextButton()
            }
        }
    }

    companion object {
    }

}