package com.teumteum.teumteum.presentation.signup.name

import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.teumteum.base.BindingFragment
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.FragmentGetNameBinding
import com.teumteum.teumteum.presentation.signup.SignUpActivity
import com.teumteum.teumteum.presentation.signup.SignUpViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.regex.Pattern

@AndroidEntryPoint
class GetNameFragment
    : BindingFragment<FragmentGetNameBinding>(R.layout.fragment_get_name) {

    private val viewModel by activityViewModels<SignUpViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        binding.lifecycleOwner = this
        binding.etName.filters = arrayOf(filterAlphaNumSpace)
        setTextChangedListener()
        checkValidinput()
    }

    private fun setTextChangedListener() {
        binding.etName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                viewModel.updateUserName(p0.toString())
            }
        })
    }

    private var filterAlphaNumSpace = InputFilter { source, _, _, _, _, _ ->
        val ps = Pattern.compile(REGEX_NAME_PATTERN_WRITING)
        if (!ps.matcher(source).matches()) {
            ""
        } else source
    }

    private fun checkValidinput() {
        lifecycleScope.launch {
            viewModel.userName.collect { userName ->
                if (Pattern.matches(REGEX_NAME_PATTERN_SUBMIT, userName) && userName.trim().length >= 2) {
                    (activity as SignUpActivity).activateNextButton()
                    binding.tvCaption.visibility = View.INVISIBLE
                }
                else {
                    (activity as SignUpActivity).disableNextButton()
                    binding.tvCaption.visibility = View.VISIBLE
                }
            }
        }
    }

    companion object {
        private const val REGEX_NAME_PATTERN_SUBMIT = "^([가-힣]*)\$"
        private const val REGEX_NAME_PATTERN_WRITING = "^[ㄱ-ㅣ가-힣]+$"
    }
}