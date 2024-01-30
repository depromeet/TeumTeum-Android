package com.teumteum.teumteum.presentation.signup.goal

import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.teumteum.base.BindingFragment
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.FragmentGetGoalBinding
import com.teumteum.teumteum.presentation.signup.SignUpActivity
import com.teumteum.teumteum.presentation.signup.SignUpViewModel
import kotlinx.coroutines.launch

class GetGoalFragment
    : BindingFragment<FragmentGetGoalBinding>(R.layout.fragment_get_goal) {

    private val viewModel by activityViewModels<SignUpViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        binding.lifecycleOwner = this
        setTextChangedListener()
        setEditTextsFilter()
        checkValidInput()
    }

    private fun setEditTextsFilter() {
        val maxLength = 49 // 최대 글자 수
        val filterArray = arrayOf<InputFilter>(InputFilter.LengthFilter(maxLength), InputFilter { source, _, _, _, _, _ ->
            // 엔터('\n')를 필터링
            source?.let {
                for (i in it.indices) {
                    if (it[i] == '\n') {
                        return@InputFilter "" // 엔터를 무시하고 빈 문자열 반환
                    }
                }
            }
            null
        })

        binding.etGoal.filters = filterArray
    }

    private fun setTextChangedListener() {
        binding.etGoal.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                viewModel.updateGoalText(p0.toString())
            }
        })
    }

    private fun checkValidInput() {
        lifecycleScope.launch {
            viewModel.goalText.collect { goal ->
                if (goal.trim().length in 1..50)
                    (activity as SignUpActivity).activateNextButton()
                else
                    (activity as SignUpActivity).disableNextButton()
            }
        }
    }

    companion object {
    }
}