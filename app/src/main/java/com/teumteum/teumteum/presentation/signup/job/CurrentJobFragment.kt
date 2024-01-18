package com.teumteum.teumteum.presentation.signup.job

import android.app.ProgressDialog.show
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

        jobClassBottomSheet = SingleModalBottomSheet.newInstance("직군 입력", jobSort, jobClassListener)

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
                if (viewModel.jobClass.value in jobSort) {
                    jobDetailList = when (viewModel.jobClass.value) {
                        JOB_DESIGN -> jobDesigner
                        JOB_DEVELOPMENT -> jobDev
                        JOB_PLANNING -> jobManager
                        else -> ArrayList()
                    }
                    if (viewModel.jobClass.value in jobSort) {
                        jobDetailClassBottomSheet = SingleModalBottomSheet.newInstance("직무 입력", jobDetailList, jobDetailClassListener)
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
        const val JOB_DESIGN = "디자인"
        const val JOB_DEVELOPMENT = "개발"
        const val JOB_PLANNING = "기획"

        val jobSort = arrayListOf(JOB_DESIGN, JOB_DEVELOPMENT, JOB_PLANNING)

        val jobDesigner = arrayListOf(
            "프로덕트 디자이너", "BX 디자이너", "그래픽 디자이너",
            "영상 디자이너", "UX 디자이너", "UI 디자이너", "플랫폼 디자이너"
        )

        val jobDev = arrayListOf(
            "BE 개발자", "iOS 개발자", "AOS 개발자", "FE 개발자"
        )

        val jobManager = arrayListOf(
            "PO", "PM", "서비스 기획자"
        )
    }
}