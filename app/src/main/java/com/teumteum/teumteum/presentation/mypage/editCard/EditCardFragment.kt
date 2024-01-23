package com.teumteum.teumteum.presentation.mypage.editCard

import android.app.ProgressDialog.show
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.teumteum.base.BindingFragment
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.FragmentEditCardBinding
import com.teumteum.teumteum.presentation.MainActivity
import com.teumteum.teumteum.presentation.moim.MoimViewModel
import com.teumteum.teumteum.presentation.mypage.setting.viewModel.EditCardViewModel
import com.teumteum.teumteum.presentation.mypage.setting.viewModel.MyPageViewModel
import com.teumteum.teumteum.presentation.mypage.setting.viewModel.SheetEvent
import com.teumteum.teumteum.presentation.signup.SignUpActivity
import com.teumteum.teumteum.presentation.signup.area.PreferredAreaFragment
import com.teumteum.teumteum.presentation.signup.job.CurrentJobFragment
import com.teumteum.teumteum.presentation.signup.job.ReadyJobFragment
import com.teumteum.teumteum.presentation.signup.mbti.GetMbtiFragment
import com.teumteum.teumteum.presentation.signup.modal.AreaModalBottomSheet
import com.teumteum.teumteum.presentation.signup.modal.MbtiModalBottomSheet
import com.teumteum.teumteum.presentation.signup.modal.SingleModalAdapter
import com.teumteum.teumteum.presentation.signup.modal.SingleModalBottomSheet
import com.teumteum.teumteum.util.SignupUtils
import com.teumteum.teumteum.util.SignupUtils.JOB_DESIGN
import com.teumteum.teumteum.util.SignupUtils.JOB_DESIGN_LIST
import com.teumteum.teumteum.util.SignupUtils.JOB_DEVELOPMENT
import com.teumteum.teumteum.util.SignupUtils.JOB_DEV_LIST
import com.teumteum.teumteum.util.SignupUtils.JOB_PLANNING
import com.teumteum.teumteum.util.SignupUtils.JOB_PLAN_LIST
import com.teumteum.teumteum.util.SignupUtils.JOB_SORT_LIST
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


        val jobDetailClassListener: (String) -> Unit = { item ->
            viewModel.updateJobDetailClass(item)
            jobDetailClassBottomSheet?.dismiss()
        }


        // Initialize with an empty list, it will be updated later based on the selected job class
        jobDetailClassBottomSheet = SingleModalBottomSheet.newInstance(
            ReadyJobFragment.BOTTOM_SHEET_DETAIL_TITLE, arrayListOf(), jobDetailClassListener)
    }

    private fun updateJobDetailList(jobClass: String?) {
        jobDetailList = when (jobClass) {
            JOB_DESIGN -> JOB_DESIGN_LIST
            JOB_DEVELOPMENT -> JOB_DEV_LIST
            JOB_PLANNING -> JOB_PLAN_LIST
            else -> ArrayList()
        }

        // Assuming your SingleModalBottomSheet class has a method to update its list
        jobDetailClassBottomSheet?.updateList(jobDetailList)
    }

    private fun setupEventObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.sheetEvent.collect { event ->
                when (event) {
                    SheetEvent.JobDetail -> showJobDetailSheet()
                    SheetEvent.JobClass -> showJobClassSheet()
                    SheetEvent.Mbti -> showMbtiSheet(viewModel)
                    SheetEvent.Area -> showAreaSheet()
                    else -> {}
                }
            }
        }
    }

    private fun showMbtiSheet(viewModel: EditCardViewModel) {
        mbtiBottomSheet = MbtiModalBottomSheet.newInstance("MBTI") { mbti ->
            viewModel.updateMbtiText(mbti)
            mbtiBottomSheet?.dismiss()
            viewModel.triggerSheetEvent(SheetEvent.Dismiss)
        }
        mbtiBottomSheet!!.show(childFragmentManager, "MbtiModalBottomSheet")
    }

    private fun showJobClassSheet() {
        val jobClassListener: (String) -> Unit = { item ->
            viewModel.updateJobClass(item)
            viewModel.updateJobDetailClass("")
            updateJobDetailList(item)
            jobClassBottomSheet?.dismiss()
            viewModel.triggerSheetEvent(SheetEvent.Dismiss)
        }
        val jobClassBottomSheet = SingleModalBottomSheet.newInstance(
            ReadyJobFragment.BOTTOM_SHEET_TITLE,
            JOB_SORT_LIST, jobClassListener)
        jobClassBottomSheet.show(childFragmentManager, SingleModalBottomSheet.TAG)
    }

    private fun showJobDetailSheet() {
        val jobDetailClassListener: (String) -> Unit = { item ->
            viewModel.updateJobDetailClass(item)
            jobDetailClassBottomSheet?.dismiss()
            viewModel.triggerSheetEvent(SheetEvent.Dismiss)
        }
        if (viewModel.jobClass.value in JOB_SORT_LIST) {
            jobDetailList = when (viewModel.jobClass.value) {
                JOB_DESIGN -> JOB_DESIGN_LIST
                JOB_DEVELOPMENT -> JOB_DEV_LIST
                JOB_PLANNING -> JOB_PLAN_LIST
                else -> ArrayList()
            }
            if (viewModel.jobClass.value in JOB_SORT_LIST) {
                jobDetailClassBottomSheet = SingleModalBottomSheet.newInstance(
                    CurrentJobFragment.BOTTOM_SHEET_DETAIL_TITLE,
                    jobDetailList,
                    jobDetailClassListener
                )
                jobDetailClassBottomSheet?.apply {
                    setSelectedItem(viewModel.jobDetailClass.value)
                }
            }
            jobDetailClassBottomSheet?.show(childFragmentManager, SingleModalBottomSheet.TAG)
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
            viewModel.triggerSheetEvent(SheetEvent.Dismiss)
        }
        
        areaBottomSheet = AreaModalBottomSheet.newInstance(PreferredAreaFragment.BOTTOM_SHEET_TITLE, cityListener, streetListener)

        areaBottomSheet?.apply {
            setSelectedStreet(viewModel.preferredCity.value, viewModel.preferredStreet.value)
        }
        areaBottomSheet!!.show(childFragmentManager, AreaModalBottomSheet.TAG)
    }


    companion object {
        const val BOTTOM_SHEET_TITLE = "직군 입력"
        const val BOTTOM_SHEET_DETAIL_TITLE = "직무 입력"
    }
}
