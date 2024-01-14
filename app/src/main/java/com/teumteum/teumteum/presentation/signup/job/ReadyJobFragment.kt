package com.teumteum.teumteum.presentation.signup.job

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
    private val jobSort = ArrayList<String>()
    private val jobDesigner = ArrayList<String>()
    private val jobDev = ArrayList<String>()
    private val jobManager = ArrayList<String>()
    private var jobDetailList = ArrayList<String>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        binding.lifecycleOwner = this
        initJobInfo()
        initBottomSheet()
        checkValidInput()
    }

    private fun initJobInfo() {
        jobSort.addAll(arrayOf("디자인", "개발", "기획"))
        jobDesigner.addAll(arrayOf("프로덕트 디자이너", "BX 디자이너", "그래픽 디자이너",
            "영상 디자이너", "UX 디자이너", "UI 디자이너", "플랫폼 디자이너"))
        jobDev.addAll(arrayOf("BE 개발자", "iOS 개발자", "AOS 개발자", "FE 개발자"))
        jobManager.addAll(arrayOf("PO", "PM", "서비스 기획자"))
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
                jobClassBottomSheet?.setFocusedImageView(ivShowWho)
                jobClassBottomSheet?.setSelectedItem(viewModel.readyJobClass.value)
                jobClassBottomSheet?.show(childFragmentManager, SingleModalBottomSheet.TAG)
                ivShowWho.setImageResource(R.drawable.ic_arrow_up_l)
            }

            llWhat.setOnClickListener {
                if (viewModel.readyJobClass.value in jobSort) {
                    jobDetailList = when (viewModel.readyJobClass.value) {
                        "디자인" -> jobDesigner
                        "개발" -> jobDev
                        "기획" -> jobManager
                        else -> ArrayList()
                    }
                    if (viewModel.readyJobClass.value in jobSort) {
                        jobDetailClassBottomSheet = SingleModalBottomSheet.newInstance("직군 입력", jobDetailList, jobDetailClassListener)
                        jobDetailClassBottomSheet?.setFocusedImageView(ivShowWhat)
                        jobDetailClassBottomSheet?.setSelectedItem(viewModel.readyJobDetailClass.value)
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
    }
}