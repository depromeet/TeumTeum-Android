package com.teumteum.teumteum.presentation.moim

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.teumteum.base.BindingFragment
import com.teumteum.base.component.compose.theme.ColorPalette_Dark
import com.teumteum.base.component.compose.theme.ColorPalette_Light
import com.teumteum.base.component.compose.theme.TmtmColorPalette
import com.teumteum.base.util.extension.defaultToast
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.FragmentModifyMoimBinding
import com.teumteum.teumteum.presentation.MainActivity
import com.teumteum.teumteum.presentation.moim.compose.MoimModify
import com.teumteum.teumteum.presentation.mypage.setting.viewModel.SheetEvent
import com.teumteum.teumteum.presentation.signup.modal.SingleModalBottomSheet
import com.teumteum.teumteum.util.SignupUtils.PEOPLE_LIST
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.ArrayList

class MoimModifyFragment: BindingFragment<FragmentModifyMoimBinding>(R.layout.fragment_modify_moim){
    private val viewModel: MoimViewModel by activityViewModels()
    private var topicBottomSheet: SingleModalBottomSheet? = null
    private var peopleBottomSheet: SingleModalBottomSheet? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = findNavController()
        (activity as MainActivity).hideBottomNavi()

        val meetingId = arguments?.getLong("meetingId")

        // viewModel에 meetingId 설정
        meetingId?.let {
            viewModel.setMeetingId(it)
        }

        observe()
        observeBottomSheet()

        binding.composeModifyMoim.setContent {
            CompositionLocalProvider(TmtmColorPalette provides if(isSystemInDarkTheme()) ColorPalette_Dark else ColorPalette_Light ) {
                MoimModify(viewModel, navController)
            }
        }
    }

    private fun observe() {
        viewModel.screenState.flowWithLifecycle(lifecycle)
            .onEach {
                when(it) {
                    ScreenState.Failure -> { context?.defaultToast(getString(R.string.setting_dialog_moim_delete_failure)) }
                    ScreenState.Server -> { context?.defaultToast(getString(R.string.moim_alert_message_server)) }
                    ScreenState.DeleteSuccess -> {context?.defaultToast(getString(R.string.setting_dialog_moim_delete_success))}
                    ScreenState.Success -> {context?.defaultToast(getString(R.string.moim_alert_message_modify_success))}
                    ScreenState.ModifyFailure -> {context?.defaultToast(getString(R.string.setting_dialog_moim_modify_failure))}
                    else -> {}
                }
            }
            .launchIn(lifecycleScope)
    }

    private fun observeBottomSheet() {
        viewModel.bottomSheet.flowWithLifecycle(lifecycle)
            .onEach {
                when(it) {
                    BottomSheet.Default -> {}
                    BottomSheet.Topic -> showTopicSheet()
                    BottomSheet.People -> showPeopleSheet()
                    else -> {}
                }
            }
            .launchIn(lifecycleScope)
    }

    private fun showTopicSheet() {
        val topicClassListener: (String) -> Unit = { item ->
            TopicType.fromTitle(item)?.let {
                viewModel.updateTopic(it)
            }
            topicBottomSheet?.dismiss()
            viewModel.updateBottomSheet(BottomSheet.Default)
        }

        val topicTitles = TopicType.values().map { it.title }
        topicBottomSheet = SingleModalBottomSheet.newInstance("모임 주제", topicTitles as ArrayList, topicClassListener
        ).apply {
            dismissListener = object: SingleModalBottomSheet.OnDismissListener {
                override fun onDismiss() {
                    viewModel.updateBottomSheet(BottomSheet.Default)
                }
            }
        }
        topicBottomSheet?.show(childFragmentManager, SingleModalBottomSheet.TAG)
    }

    private fun extractNumberFromString(text: String): Int? {
        return text.filter { it.isDigit() }.toIntOrNull()
    }

    private fun showPeopleSheet() {
        val peopleClassListener: (String) -> Unit = { item ->
            extractNumberFromString(item)?.let {
                viewModel.updatePeople(it)
            }
            peopleBottomSheet?.dismiss()
            viewModel.updateBottomSheet(BottomSheet.Default)
        }

        peopleBottomSheet = SingleModalBottomSheet.newInstance(
            "참여 인원 선택", PEOPLE_LIST, peopleClassListener
        ).apply {
            dismissListener = object: SingleModalBottomSheet.OnDismissListener {
                override fun onDismiss() {
                    viewModel.updateBottomSheet(BottomSheet.Default)
                }
            }
        }
        peopleBottomSheet?.show(childFragmentManager, SingleModalBottomSheet.TAG)
    }

}