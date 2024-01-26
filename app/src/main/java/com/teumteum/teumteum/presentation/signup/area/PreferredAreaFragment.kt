package com.teumteum.teumteum.presentation.signup.area

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.teumteum.base.BindingFragment
import com.teumteum.base.util.extension.setOnSingleClickListener
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.FragmentPreferredAreaBinding
import com.teumteum.teumteum.presentation.signup.SignUpActivity
import com.teumteum.teumteum.presentation.signup.SignUpViewModel
import com.teumteum.teumteum.presentation.signup.modal.AreaModalBottomSheet
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PreferredAreaFragment
    : BindingFragment<FragmentPreferredAreaBinding>(R.layout.fragment_preferred_area) {

    private val viewModel by activityViewModels<SignUpViewModel>()
    private var bottomSheet: AreaModalBottomSheet? = null
    private var focusedCity: String = INITIAL_FOCUSED_CITY

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        binding.lifecycleOwner = this
        initBottomSheet()
        checkValidInput()
    }

    private fun initBottomSheet() {
        val cityListener: (String) -> Unit = { city ->
            focusedCity = city
            bottomSheet?.setFocusedCity(focusedCity)
        }

        val streetListener: (String) -> Unit = { street ->
            viewModel.updatePreferredArea(focusedCity, street)
            bottomSheet?.dismiss()
        }

        bottomSheet = AreaModalBottomSheet.newInstance(BOTTOM_SHEET_TITLE, cityListener, streetListener)

        with(binding) {
            llStatus.setOnSingleClickListener {
                bottomSheet?.setFocusedImageView(ivShow)
                bottomSheet?.setSelectedStreet(viewModel.preferredCity.value, viewModel.preferredStreet.value)
                bottomSheet?.show(childFragmentManager, AreaModalBottomSheet.TAG)
                ivShow.setImageResource(R.drawable.ic_arrow_up_l)
            }
        }
    }

    private fun checkValidInput() {
        lifecycleScope.launch {
            viewModel.preferredStreet.collect { street ->
                if (street.isNotBlank()) (activity as SignUpActivity).activateNextButton()
                else (activity as SignUpActivity).disableNextButton()
            }
        }
    }

    companion object {
        const val BOTTOM_SHEET_TITLE = "관심 지역"
        const val INITIAL_FOCUSED_CITY = "서울"
    }
}