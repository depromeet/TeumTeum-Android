package com.teumteum.teumteum.presentation.signup.interests

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.addCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import com.teumteum.base.BindingFragment
import com.teumteum.base.util.extension.defaultSnackBar
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

        val isFromSpecialPath = arguments?.getBoolean("isFromSpecialPath", false) ?: false
        if (isFromSpecialPath) {
            val selectedInterests = arguments?.getStringArrayList("selectedInterests") ?: arrayListOf()
            initChipsWithValue(selectedInterests)
            returnInterest()
        } else {
            initSelfChips()
            initFieldChips()
        }

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
                if (isChecked) {
                    // Chip이 선택되었을 때 실행되는 코드
                    if (viewModel.interestCount.value < MAXIMUM_CHIP_COUNT) {
                        viewModel.addInterestSelf(buttonView.text.toString())
                        viewModel.updateInterestCount()
                    }
                    else {
                        context?.defaultSnackBar(view, getString(R.string.signup_tv_interset_toast),
                            (activity as SignUpActivity).findViewById(R.id.btn_next_signup))
                        buttonView.isChecked = false
                    }
                } else {
                    // Chip이 해제되었을 때 실행되는 코드
                    viewModel.removeInterestSelf(buttonView.text.toString())
                    viewModel.updateInterestCount()
                }
            }
            binding.cgInterest1.addView(chip)
        }
    }

    private fun returnInterest() {
        val interestList = ArrayList<String>().apply {
            addAll(viewModel.interestSelf.value)
            addAll(viewModel.interestField.value)
        }
        val data = Intent().apply {
            putStringArrayListExtra("changedInterests", interestList)
            Log.d("changedInterest", interestList.toString())
        }
        targetFragment?.onActivityResult(targetRequestCode, Activity.RESULT_OK, data)
    }

    private fun initChipsWithValue(selectedInterests: ArrayList<String>) {
        val interestArray = resources.getStringArray(R.array.interest_1)
        val fieldArray = resources.getStringArray(R.array.interest_2)

        // 개인 칩 초기화
        for (interest in interestArray) {
            val chip = makeChip(interest)
            val isChecked = interest in selectedInterests
            chip.isChecked = interest in selectedInterests
            setupChipListener(chip, isSelf = true)
            binding.cgInterest1.addView(chip)
            if (isChecked) {
                viewModel.addInterestSelf(interest)
            }
        }

        // 필드 칩 초기화
        for (field in fieldArray) {
            val chip = makeChip(field)
            val isChecked = field in selectedInterests
            chip.isChecked = field in selectedInterests
            setupChipListener(chip, isSelf = false)
            binding.cgInterest2.addView(chip)
            if (isChecked) {
                viewModel.addInterestField(field)
            }
        }
    }

    private fun setupChipListener(chip: Chip, isSelf: Boolean) {
        chip.setOnCheckedChangeListener { buttonView, isChecked ->
            val interest = buttonView.text.toString()

            if (isChecked) {
                if (viewModel.interestCount.value < MAXIMUM_CHIP_COUNT) {
                    if (isSelf) {
                        viewModel.addInterestSelf(interest)
                        Log.d("viewModel  field", viewModel.interestField.value.toString())
                        Log.d("viewModel chip self", viewModel.interestSelf.value.toString())
                    } else {
                        viewModel.addInterestField(interest)
                        Log.d("viewModel chip field", viewModel.interestField.value.toString())
                        Log.d("viewModel chip self", viewModel.interestSelf.value.toString())
                    }
                    viewModel.updateInterestCount()
                } else {
                    context?.defaultSnackBar(view, getString(R.string.signup_tv_interset_toast),
                        (activity as SignUpActivity).findViewById(R.id.btn_next_signup))
                    chip.isChecked = false
                }
            } else {
                if (isSelf) {
                    viewModel.removeInterestSelf(interest)
                    Log.d("viewModel chip field", viewModel.interestField.value.toString())
                    Log.d("viewModel chip self", viewModel.interestSelf.value.toString())
                } else {
                    viewModel.removeInterestField(interest)
                    Log.d("viewModel chip field", viewModel.interestField.value.toString())
                    Log.d("viewModel chip self", viewModel.interestSelf.value.toString())
                }
                viewModel.updateInterestCount()
            }
        }
    }

    private fun initFieldChips() {
        val interestField = viewModel.interestField.value
        val interestArray = resources.getStringArray(R.array.interest_2)
        for (interest in interestArray) {
            val chip = makeChip(interest)
            if (interest in interestField) chip.isChecked = true
            chip.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    // Chip이 선택되었을 때 실행되는 코드
                    if (viewModel.interestCount.value < MAXIMUM_CHIP_COUNT) {
                        viewModel.addInterestField(buttonView.text.toString())
                        viewModel.updateInterestCount()
                    }
                    else {
                        context?.defaultSnackBar(view, getString(R.string.signup_tv_interset_toast),
                            (activity as SignUpActivity).findViewById(R.id.btn_next_signup))
                        buttonView.isChecked = false
                    }

                } else {
                    // Chip이 해제되었을 때 실행되는 코드
                    viewModel.removeInterestField(buttonView.text.toString())
                    viewModel.updateInterestCount()
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

    companion object {
        const val MAXIMUM_CHIP_COUNT = 3
    }
}