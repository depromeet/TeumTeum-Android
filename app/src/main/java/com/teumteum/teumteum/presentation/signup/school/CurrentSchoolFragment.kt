package com.teumteum.teumteum.presentation.signup.school

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.teumteum.base.BindingFragment
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.FragmentCurrentSchoolBinding
import com.teumteum.teumteum.presentation.signup.SignUpActivity
import com.teumteum.teumteum.presentation.signup.SignUpViewModel
import com.teumteum.teumteum.presentation.signup.name.GetNameFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.regex.Pattern

@AndroidEntryPoint
class CurrentSchoolFragment
    : BindingFragment<FragmentCurrentSchoolBinding>(R.layout.fragment_current_school) {

    private val viewModel by activityViewModels<SignUpViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        binding.lifecycleOwner = this
        setTextChangedListener()
        checkValidInput()
    }

    private fun setTextChangedListener() {
        binding.etSchool.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                viewModel.updateSchoolName(p0.toString())
            }
        })
    }

    private fun checkValidInput() {
        lifecycleScope.launch {
            viewModel.schoolName.collect { schoolName ->
                if (schoolName.trim().length in 2..13)
                    (activity as SignUpActivity).activateNextButton()
                else
                    (activity as SignUpActivity).disableNextButton()
            }
        }
    }

    companion object {
    }
}