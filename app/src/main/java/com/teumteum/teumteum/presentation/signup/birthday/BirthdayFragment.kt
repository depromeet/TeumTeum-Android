package com.teumteum.teumteum.presentation.signup.birthday

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.textfield.TextInputLayout
import com.teumteum.base.BindingFragment
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.FragmentBirthdayBinding
import com.teumteum.teumteum.presentation.signup.SignUpActivity
import com.teumteum.teumteum.presentation.signup.SignUpViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BirthdayFragment
    : BindingFragment<FragmentBirthdayBinding>(R.layout.fragment_birthday) {

    private val viewModel by activityViewModels<SignUpViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        binding.lifecycleOwner = this
        setTextChangedListener()
        checkValidInput()
    }

    private fun setTextChangedListener() {
        binding.etBdayYear.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                viewModel.updateBirthYear(p0.toString())
            }
        })

        binding.etBdayMonth.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                viewModel.updateBirthMonth(p0.toString())
            }
        })

        binding.etBdayDate.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                viewModel.updateBirthDate(p0.toString())
            }
        })
    }

    private fun checkValidInput() {
        lifecycleScope.launch {
            viewModel.birthValid.collect { birthValid ->
                if (birthValid)
                {
                    binding.tvError.visibility = View.INVISIBLE
                    (activity as SignUpActivity).activateNextButton()
                }
                else
                    (activity as SignUpActivity).disableNextButton()
            }
        }
        with(binding) {
            val textInputLayouts = arrayOf(tilBdayYear, tilBdayDate, tilBdayMonth)
            applyFocusChangeListeners(textInputLayouts)
        }
    }

    private fun applyFocusChangeListeners(tils: Array<TextInputLayout>) {
        tils.forEach { til ->
            til.editText?.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                if (hasFocus) til.setBackgroundResource(R.drawable.shape_rect4_elevation_level01_active)
                else {
                    val isValid = when (til.id) {
                        R.id.til_bday_year -> viewModel.birthYear.value.toIntOrNull() in 1000..2122
                        R.id.til_bday_month -> viewModel.birthMonth.value.toIntOrNull() in 1..12
                        R.id.til_bday_date -> viewModel.birthDate.value.toIntOrNull() in 1..31
                        else -> false
                    }
                    if (isValid) til.setBackgroundResource(R.drawable.shape_rect4_elevation_level01)
                    else til.setBackgroundResource(R.drawable.shape_rect4_elevation_level01_error)
                    if (!viewModel.birthValid.value) binding.tvError.visibility = View.VISIBLE
                }
            }
        }
    }

    companion object {
    }
}