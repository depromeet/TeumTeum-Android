package com.teumteum.teumteum.presentation.signup.community

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.teumteum.base.BindingFragment
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.FragmentCommunityBinding
import com.teumteum.teumteum.presentation.signup.SignUpViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommunityFragment
    : BindingFragment<FragmentCommunityBinding>(R.layout.fragment_community) {

    private val viewModel by activityViewModels<SignUpViewModel>()
//    private var bottomSheet: SingleModalBottomSheet? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
    }

    companion object {
    }
}