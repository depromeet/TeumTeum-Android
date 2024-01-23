package com.teumteum.teumteum.presentation.mypage.editCard

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.teumteum.base.BindingFragment
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.FragmentEditCardBinding
import com.teumteum.teumteum.presentation.MainActivity
import com.teumteum.teumteum.presentation.mypage.setting.viewModel.EditCardViewModel
import com.teumteum.teumteum.presentation.mypage.setting.viewModel.MyPageViewModel
import com.teumteum.teumteum.presentation.mypage.setting.viewModel.SheetEvent
import com.teumteum.teumteum.presentation.signup.modal.AreaModalBottomSheet
import com.teumteum.teumteum.presentation.signup.modal.MbtiModalBottomSheet
import com.teumteum.teumteum.presentation.signup.modal.SingleModalBottomSheet
import kotlinx.coroutines.launch

class EditCardFragment: BindingFragment<FragmentEditCardBinding>(R.layout.fragment_edit_card) {
    private val viewModel: EditCardViewModel by activityViewModels()
    private val myPageViewModel: MyPageViewModel by activityViewModels()
    private var mbtiBottomSheet: MbtiModalBottomSheet? = null
    private var jobClassBottomSheet: SingleModalBottomSheet? = null
    private var jobDetailClassBottomSheet: SingleModalBottomSheet? = null
    private var areaBottomSheet: AreaModalBottomSheet? = null
    private var focusedCity: String = "서울"
    var jobDetailList = ArrayList<String>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = findNavController()
        (activity as MainActivity).hideBottomNavi()

        setupEventObserver()
        initBottomSheet()

        binding.composeEditCard.setContent {
            EditCardScreen(myPageViewModel, viewModel, navController)
        }

    }

    val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            (activity as MainActivity).showBottomNavi()
        }
    }

    private fun initBottomSheet() {
        val jobClassListener: (String) -> Unit = { jobClass ->
            viewModel.updateJobClass(jobClass)
            jobClassBottomSheet?.dismiss()
        }

        jobClassBottomSheet = SingleModalBottomSheet.newInstance("직군 입력", jobSort, jobClassListener)

        val jobDetailClassListener: (String) -> Unit = { jobDetailClass ->
            viewModel.updateJobDetailClass(jobDetailClass)
            jobDetailClassBottomSheet?.dismiss()
        }
        jobDetailClassBottomSheet = SingleModalBottomSheet.newInstance("직무 입력",jobDetailList,jobDetailClassListener)


        val listener: (String) -> Unit = { item ->
            viewModel.updateMbtiText(item)
            mbtiBottomSheet?.dismiss()
        }
    }

    private fun setupEventObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.sheetEvent.collect { event ->
                when (event) {
                    SheetEvent.JobDetail -> showJobDetailSheet()
                    SheetEvent.JobClass -> showJobClassSheet()
                    SheetEvent.Mbti -> showMbtiSheet()
                    SheetEvent.Area -> showAreaSheet()
                    else -> {}
                }
            }
        }
    }

    private fun showMbtiSheet() {
        val mbtiBottomSheet = MbtiModalBottomSheet.newInstance("MBTI") { mbti ->
            viewModel.updateMbtiText(mbti)
            mbtiBottomSheet?.dismiss()
        }
        mbtiBottomSheet.show(childFragmentManager, "MbtiModalBottomSheet")
    }

    private fun reloadLastMbti() {
        val mbtiBoolean = BooleanArray(4)
        val mbtiCharArray = viewModel.mbtiText.value.toCharArray()
        if (mbtiCharArray.size == 4) {
            mbtiBoolean[0] = mbtiCharArray[0] == 'E'
            mbtiBoolean[1] = mbtiCharArray[1] == 'N'
            mbtiBoolean[2] = mbtiCharArray[2] == 'F'
            mbtiBoolean[3] = mbtiCharArray[3] == 'P'
            mbtiBottomSheet?.initSelectedMbti(mbtiBoolean[0], mbtiBoolean[1], mbtiBoolean[2], mbtiBoolean[3])
        }
        else mbtiBottomSheet?.initDefaultMbti()
    }


    private fun showJobClassSheet() {
        val jobClassListener: (String) -> Unit = { jobClass ->
            viewModel.updateJobClass(jobClass)
            viewModel.updateJobDetailClass("")
            jobClassBottomSheet?.dismiss()
        }

        jobClassBottomSheet = SingleModalBottomSheet.newInstance("직군 입력", jobSort, jobClassListener)
        jobClassBottomSheet?.apply {
            setSelectedItem(viewModel.jobClass.value)
            show(childFragmentManager, SingleModalBottomSheet.TAG)
        }
    }

    private fun showJobDetailSheet() {
        jobDetailList = when (viewModel.jobClass.value) {
            JOB_DESIGN -> jobDesigner
            JOB_DEVELOPMENT -> jobDev
            JOB_PLANNING -> jobManager
            else -> ArrayList()
        }

        val jobDetailClassListener: (String) -> Unit = { jobDetailClass ->
            viewModel.updateJobDetailClass(jobDetailClass)
            jobDetailClassBottomSheet?.dismiss()
        }

        jobDetailClassBottomSheet = SingleModalBottomSheet.newInstance(
            "직무 입력",
            jobDetailList,
            jobDetailClassListener
        )
        jobDetailClassBottomSheet?.apply {
            setSelectedItem(viewModel.jobDetailClass.value)
            show(childFragmentManager, SingleModalBottomSheet.TAG)
        }
    }

    private fun showAreaSheet() {
        val cityListener: (String) -> Unit = { city ->
            focusedCity = city
            areaBottomSheet?.setFocusedCity(focusedCity)
        }

        val streetListener: (String) -> Unit = { street ->
            viewModel.updatePreferredArea(focusedCity, street)
            areaBottomSheet?.dismiss()
        }

        areaBottomSheet = AreaModalBottomSheet.newInstance("관심 지역", cityListener, streetListener)
        areaBottomSheet?.apply {
            setSelectedStreet(viewModel.preferredCity.value, viewModel.preferredStreet.value)
            show(childFragmentManager, AreaModalBottomSheet.TAG)
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