package com.teumteum.teumteum.presentation.signup.job

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.teumteum.base.BindingFragment
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.FragmentCurrentJobBinding
import com.teumteum.teumteum.presentation.signup.SignUpActivity
import com.teumteum.teumteum.presentation.signup.SignUpViewModel
import com.teumteum.teumteum.presentation.signup.modal.SingleModalBottomSheet
import com.teumteum.teumteum.util.SignupUtils.JOB_DESIGN
import com.teumteum.teumteum.util.SignupUtils.JOB_DESIGN_LIST
import com.teumteum.teumteum.util.SignupUtils.JOB_DEVELOPMENT
import com.teumteum.teumteum.util.SignupUtils.JOB_DEV_LIST
import com.teumteum.teumteum.util.SignupUtils.JOB_PLANNING
import com.teumteum.teumteum.util.SignupUtils.JOB_PLAN_LIST
import com.teumteum.teumteum.util.SignupUtils.JOB_SORT_LIST
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CurrentJobFragment
    : BindingFragment<FragmentCurrentJobBinding>(R.layout.fragment_current_job) {

    private val viewModel by activityViewModels<SignUpViewModel>()
    private var jobClassBottomSheet: SingleModalBottomSheet? = null
    private var jobDetailClassBottomSheet: SingleModalBottomSheet? = null
    private var jobDetailList = ArrayList<String>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        binding.lifecycleOwner = this
        setTextChangedListener()
        initBottomSheet()
        checkValidInput()
    }

    private fun setTextChangedListener() {
        binding.etWhere.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                viewModel.updateCompanyName(p0.toString())
            }
        })
    }

    private fun initBottomSheet() {
        val jobClassListener: (String) -> Unit = { item ->
            viewModel.updateJobClass(item)
            viewModel.updateJobDetailClass("")
            jobClassBottomSheet?.dismiss()
        }

        val jobDetailClassListener: (String) -> Unit = { item ->
            viewModel.updateJobDetailClass(item)
            jobDetailClassBottomSheet?.dismiss()
        }

        jobClassBottomSheet = SingleModalBottomSheet.newInstance(BOTTOM_SHEET_TITLE, JOB_SORT_LIST, jobClassListener)

        with(binding) {
            llWho.setOnClickListener {
                jobClassBottomSheet?.apply {
                    setFocusedImageView(ivShowWho)
                    setSelectedItem(viewModel.jobClass.value)
                }
                jobClassBottomSheet?.show(childFragmentManager, SingleModalBottomSheet.TAG)
                ivShowWho.setImageResource(R.drawable.ic_arrow_up_l)
            }

            llWhat.setOnClickListener {
                if (viewModel.jobClass.value in JOB_SORT_LIST) {
                    jobDetailList = when (viewModel.jobClass.value) {
                        JOB_DESIGN -> JOB_DESIGN_LIST
                        JOB_DEVELOPMENT -> JOB_DEV_LIST
                        JOB_PLANNING -> JOB_PLAN_LIST
                        else -> ArrayList()
                    }
                    if (viewModel.jobClass.value in JOB_SORT_LIST) {
                        jobDetailClassBottomSheet = SingleModalBottomSheet.newInstance(BOTTOM_SHEET_DETAIL_TITLE, jobDetailList, jobDetailClassListener)
                        jobDetailClassBottomSheet?.apply {
                            setFocusedImageView(ivShowWhat)
                            setSelectedItem(viewModel.jobDetailClass.value)
                        }
                        jobDetailClassBottomSheet?.show(childFragmentManager, SingleModalBottomSheet.TAG)
                        ivShowWhat.setImageResource(R.drawable.ic_arrow_up_l)
                    }
                }
            }
        }
    }

    private fun checkValidInput() {
        lifecycleScope.launch {
            viewModel.currentJobValid.collect { isValid ->
                if (isValid) (activity as SignUpActivity).activateNextButton()
                else (activity as SignUpActivity).disableNextButton()
            }
        }
    }

    companion object {
        const val BOTTOM_SHEET_TITLE = "직군 입력"
        const val BOTTOM_SHEET_DETAIL_TITLE = "직무 입력"
    }
}