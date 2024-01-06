package com.teumteum.teumteum.presentation.teumteum

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.teumteum.base.BindingFragment
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.FragmentTeumTeumBinding
import com.teumteum.teumteum.presentation.teumteum.shake.view.ShakeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TeumTeumFragment :
    BindingFragment<FragmentTeumTeumBinding>(R.layout.fragment_teum_teum) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btn.setOnClickListener {
            startActivity(Intent(requireActivity(), ShakeActivity::class.java))
        }
    }
}