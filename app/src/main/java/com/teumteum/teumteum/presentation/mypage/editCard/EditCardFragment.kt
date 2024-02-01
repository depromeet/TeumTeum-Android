package com.teumteum.teumteum.presentation.mypage.editCard

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.teumteum.base.BindingFragment
import com.teumteum.base.component.compose.theme.ColorPalette_Dark
import com.teumteum.base.component.compose.theme.ColorPalette_Light
import com.teumteum.base.component.compose.theme.TmtmColorPalette
import com.teumteum.base.util.extension.defaultToast
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.FragmentEditCardBinding
import com.teumteum.teumteum.presentation.MainActivity
import com.teumteum.teumteum.presentation.mypage.setting.viewModel.EditCardViewModel
import com.teumteum.teumteum.presentation.mypage.setting.viewModel.MyPageViewModel
import com.teumteum.teumteum.presentation.mypage.setting.viewModel.SheetEvent
import com.teumteum.teumteum.presentation.signup.SignUpActivity
import com.teumteum.teumteum.presentation.signup.area.PreferredAreaFragment
import com.teumteum.teumteum.presentation.signup.community.CommunityFragment
import com.teumteum.teumteum.presentation.signup.job.CurrentJobFragment
import com.teumteum.teumteum.presentation.signup.job.ReadyJobFragment
import com.teumteum.teumteum.presentation.signup.modal.AreaModalBottomSheet
import com.teumteum.teumteum.presentation.signup.modal.MbtiModalBottomSheet
import com.teumteum.teumteum.presentation.signup.modal.SingleModalBottomSheet
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
    private var mbtiBottomSheet: MbtiModalBottomSheet? = null
    private var jobClassBottomSheet: SingleModalBottomSheet? = null
    private var jobDetailClassBottomSheet: SingleModalBottomSheet? = null
    private var areaBottomSheet: AreaModalBottomSheet? = null
    private var statusBottomSheet: SingleModalBottomSheet? = null
    private var focusedCity: String = "서울"
    var jobDetailList = ArrayList<String>()
    private val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result->
        if(result.resultCode == Activity.RESULT_OK) {
            val interest = result.data?.getStringArrayListExtra("updateInterest")
            if(interest != null) {
                viewModel.setInterestField(interest)

            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = findNavController()
        (activity as MainActivity).hideBottomNavi()

        setupEventObserver()
        initBottomSheet()

        binding.composeEditCard.setContent {
            CompositionLocalProvider(TmtmColorPalette provides if(isSystemInDarkTheme()) ColorPalette_Dark else ColorPalette_Light ) {
            EditCardScreen(viewModel, navController)
            }
        }

    }

    val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            (activity as MainActivity).showBottomNavi()
            findNavController().popBackStack()
        }
    }
    private fun initBottomSheet() {

    }

    private fun updateJobDetailList(jobClass: String?) {
        jobDetailList = when (jobClass) {
            JOB_DESIGN -> JOB_DESIGN_LIST
            JOB_DEVELOPMENT -> JOB_DEV_LIST
            JOB_PLANNING -> JOB_PLAN_LIST
            else -> ArrayList()
        }

        jobDetailClassBottomSheet?.updateList(jobDetailList)
    }

    private fun setupEventObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.sheetEvent.collect { event ->
                when (event) {
                    SheetEvent.JobDetail -> showJobDetailSheet()
                    SheetEvent.JobClass -> showJobClassSheet()
                    SheetEvent.Mbti -> showMbtiSheet()
                    SheetEvent.Area -> showAreaSheet()
                    SheetEvent.Status -> showStatusSheet()
                    SheetEvent.SignUp -> { launchToSignUp() }
                    SheetEvent.Error -> {
                        context?.defaultToast("서버 통신에 오류가 발생했습니다")
                        viewModel.triggerSheetEvent(SheetEvent.None)
                    }
                    SheetEvent.Success -> {
                        context?.defaultToast("정보 수정이 완료되었습니다")
                        viewModel.triggerSheetEvent(SheetEvent.None)
                    }
                    else -> {}
                }
            }
        }
    }

    private fun launchToSignUp() {
        val intent = Intent(requireContext(), SignUpActivity::class.java).apply {
            val interests = viewModel.interestField.value
            putExtra("interests", ArrayList(interests))
            putExtra("isFromMainActivity", true)
            putExtra("navigateTo", "fragment_get_interest")
            viewModel.triggerSheetEvent(SheetEvent.Dismiss)
        }
        startForResult.launch(intent)
    }

    private fun showMbtiSheet() {
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

    private fun showStatusSheet() {
        val listener: (String) -> Unit = { item ->
            viewModel.updateCommunity(item)
            statusBottomSheet?.dismiss()
            viewModel.triggerSheetEvent(SheetEvent.Dismiss)
        }
        statusBottomSheet = SingleModalBottomSheet.newInstance(
            CommunityFragment.BOTTOM_SHEET_TITLE,
            CommunityFragment.communitySort, listener)
        statusBottomSheet?.apply {
            setSelectedItem(viewModel.community.value)
        }
        statusBottomSheet!!.show(childFragmentManager, SingleModalBottomSheet.TAG)
    }

        private fun showAreaSheet() {
        val cityListener: (String) -> Unit = { city ->
            focusedCity = city
            areaBottomSheet?.setFocusedCity(focusedCity)
            viewModel.triggerSheetEvent(SheetEvent.Dismiss)
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
    }
}
