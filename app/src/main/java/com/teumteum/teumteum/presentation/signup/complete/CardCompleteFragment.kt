package com.teumteum.teumteum.presentation.signup.complete

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.teumteum.base.BindingFragment
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.FragmentCardCompleteBinding
import com.teumteum.teumteum.presentation.signup.SignUpViewModel
import com.teumteum.teumteum.util.SignupUtils.CHARACTER_CARD_LIST
import com.teumteum.teumteum.util.SignupUtils.STATUS_STUDENT
import com.teumteum.teumteum.util.SignupUtils.STATUS_TRAINEE
import com.teumteum.teumteum.util.SignupUtils.STATUS_WORKER
import com.teumteum.teumteum.util.custom.view.model.FrontCard
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class CardCompleteFragment
    : BindingFragment<FragmentCardCompleteBinding>(R.layout.fragment_card_complete) {

    private val viewModel by activityViewModels<SignUpViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initCard()
    }

    private fun initCard() {
        with(viewModel) {
            val fc = CHARACTER_CARD_LIST[characterId.value]?.let {
                when (community.value) {
                    STATUS_WORKER -> FrontCard(userName.value, "@${companyName.value}", jobDetailClass.value,
                        "lv.1층", "${preferredArea.value}에 사는", mbtiText.value.toUpperCase(Locale.ROOT), it)
                    STATUS_STUDENT -> FrontCard(userName.value, "@${schoolName.value}", readyJobDetailClass.value,
                        "lv.1층", "${preferredArea.value}에 사는", mbtiText.value.toUpperCase(Locale.ROOT), it)
                    STATUS_TRAINEE -> FrontCard(userName.value, "@준비 중", readyJobDetailClass.value,
                        "lv.1층", "${preferredArea.value}에 사는", mbtiText.value.toUpperCase(Locale.ROOT), it)
                    else -> FrontCard()
                }
            }
            if (fc != null) binding.cardview.getInstance(fc)
        }
    }

    companion object {
    }
}