package com.teumteum.teumteum.presentation.signup.interests

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.View
import androidx.core.view.get
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import com.teumteum.base.BindingFragment
import com.teumteum.base.util.extension.toast
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.FragmentGetInterestBinding
import com.teumteum.teumteum.presentation.signup.SignUpActivity
import com.teumteum.teumteum.presentation.signup.SignUpViewModel
import com.teumteum.teumteum.util.extension.dpToPx
import kotlinx.coroutines.launch

class GetInterestFragment:
    BindingFragment<FragmentGetInterestBinding>(R.layout.fragment_get_interest) {

    private val viewModel by activityViewModels<SignUpViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        binding.lifecycleOwner = this
        initSelfChips()
        initFieldChips()
        checkValidInput()
    }

    private fun makeChip(interest: String): Chip {
        val chipDrawable = ChipDrawable.createFromAttributes(
            requireContext(),
            null,
            0,
            com.google.android.material.R.style.Widget_MaterialComponents_Chip_Choice
        )

        with(chipDrawable) {
            setChipBackgroundColorResource(R.color.color_chip_interest_fill)
            setChipStrokeColorResource(R.color.color_chip_interest_stroke)
            strokeWidth = 1.dpToPx(requireContext()).toFloat()
            textStartPadding = 16.dpToPx(requireContext()).toFloat()
            textEndPadding = 16.dpToPx(requireContext()).toFloat()
            chipMinHeight = 40.dpToPx(requireContext()).toFloat()
            text = interest
        }

        val chip = Chip(requireContext())
        with(chip) {
            setChipDrawable(chipDrawable)
            text = interest
            setTextColor(resources.getColorStateList(R.color.color_text_chip))
            chip.textAlignment = View.TEXT_ALIGNMENT_CENTER
            setTextAppearanceResource(com.teumteum.base.R.style.ta_body_1)
        }

        return chip
    }

    private fun initSelfChips() {
        val interestSelf = viewModel.interestSelf.value
        val interestArray = resources.getStringArray(R.array.interest_1)
        for (interest in interestArray) {
            val chip = makeChip(interest)
            if (interest in interestSelf) chip.isChecked = true
            chip.setOnCheckedChangeListener { buttonView, isChecked ->
                if (viewModel.interestCount.value < 3) {
                    if (isChecked) {
                        // Chip이 선택되었을 때 실행되는 코드
                        viewModel.addInterestSelf(buttonView.text.toString())
                    } else {
                        // Chip이 해제되었을 때 실행되는 코드
                        viewModel.removeInterestSelf(buttonView.text.toString())
                    }
                }
                else {
                    context?.toast(getString(R.string.signup_tv_interset_toast))
                    buttonView.isChecked = false
                }
            }
            binding.cgInterest1.addView(chip)
        }
    }

    private fun initFieldChips() {
        val interestField = viewModel.interestField.value
        val interestArray = resources.getStringArray(R.array.interest_2)
        for (interest in interestArray) {
            val chip = makeChip(interest)
            if (interest in interestField) chip.isChecked = true
            chip.setOnCheckedChangeListener { buttonView, isChecked ->
                if (viewModel.interestCount.value < 3) {
                    if (isChecked) {
                        // Chip이 선택되었을 때 실행되는 코드
                        viewModel.addInterestField(buttonView.text.toString())
                    } else {
                        // Chip이 해제되었을 때 실행되는 코드
                        viewModel.removeInterestField(buttonView.text.toString())
                    }
                }
                else {
                    context?.toast(getString(R.string.signup_tv_interset_toast))
                    buttonView.isChecked = false
                }
            }
            binding.cgInterest2.addView(chip)
        }
    }

    private fun checkValidInput() {
        lifecycleScope.launch {
            viewModel.interestCount.collect { count ->
                if (count in 1..3) (activity as SignUpActivity).activateNextButton()
                else (activity as SignUpActivity).disableNextButton()
            }
        }
    }
}