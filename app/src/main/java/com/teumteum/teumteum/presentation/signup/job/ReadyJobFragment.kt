package com.teumteum.teumteum.presentation.signup.job

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.teumteum.base.BindingFragment
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.FragmentReadyJobBinding
import com.teumteum.teumteum.presentation.signup.SignUpActivity
import com.teumteum.teumteum.presentation.signup.SignUpViewModel
import com.teumteum.teumteum.presentation.signup.modal.SingleModalBottomSheet
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ReadyJobFragment
    : BindingFragment<FragmentReadyJobBinding>(R.layout.fragment_ready_job){

    private val viewModel by activityViewModels<SignUpViewModel>()
    private var jobClassBottomSheet: SingleModalBottomSheet? = null
    private var jobDetailClassBottomSheet: SingleModalBottomSheet? = null
    private var jobDetailList = ArrayList<String>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        binding.lifecycleOwner = this
        initBottomSheet()
        checkValidInput()
    }

    private fun initBottomSheet() {
        val jobClassListener: (String) -> Unit = { item ->
            viewModel.updateReadyJobClass(item)
            viewModel.updateReadyJobDetailClass("")
            jobClassBottomSheet?.dismiss()
        }

        val jobDetailClassListener: (String) -> Unit = { item ->
            viewModel.updateReadyJobDetailClass(item)
            jobDetailClassBottomSheet?.dismiss()
        }

        jobClassBottomSheet = SingleModalBottomSheet.newInstance("직군 입력", jobSort, jobClassListener)

        with(binding) {
            llWho.setOnClickListener {
                jobClassBottomSheet?.apply {
                    setFocusedImageView(ivShowWho)
                    setSelectedItem(viewModel.readyJobClass.value)

                }
                jobClassBottomSheet?.show(childFragmentManager, SingleModalBottomSheet.TAG)
                ivShowWho.setImageResource(R.drawable.ic_arrow_up_l)
            }

            llWhat.setOnClickListener {
                if (viewModel.readyJobClass.value in jobSort) {
                    jobDetailList = when (viewModel.readyJobClass.value) {
                        JOB_DESIGN -> jobDesigner
                        JOB_DEVELOPMENT -> jobDev
                        JOB_PLANNING -> jobManager
                        else -> ArrayList()
                    }
                    if (viewModel.readyJobClass.value in jobSort) {
                        jobDetailClassBottomSheet = SingleModalBottomSheet.newInstance("직무 입력", jobDetailList, jobDetailClassListener)
                        jobDetailClassBottomSheet?.apply {
                            setFocusedImageView(ivShowWhat)
                            setSelectedItem(viewModel.readyJobDetailClass.value)
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
            viewModel.readyJobValid.collect { isValid ->
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