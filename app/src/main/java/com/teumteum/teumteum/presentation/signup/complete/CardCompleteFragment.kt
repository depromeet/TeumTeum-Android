package com.teumteum.teumteum.presentation.signup.complete

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.teumteum.base.BindingFragment
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.FragmentCardCompleteBinding
import com.teumteum.teumteum.presentation.signup.SignUpViewModel
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
            val fc = characterList[characterId.value]?.let {
                when (community.value) {
                    COMMUNITY_WORKER -> FrontCard(userName.value, "@${companyName.value}", jobDetailClass.value,
                        "lv.1층", "${preferredArea.value}에 사는", mbtiText.value.toUpperCase(Locale.ROOT), it)
                    COMMUNITY_STUDENT -> FrontCard(userName.value, "@${schoolName.value}", readyJobDetailClass.value,
                        "lv.1층", "${preferredArea.value}에 사는", mbtiText.value.toUpperCase(Locale.ROOT), it)
                    COMMUNITY_TRAINEE -> FrontCard(userName.value, "@준비 중", readyJobDetailClass.value,
                        "lv.1층", "${preferredArea.value}에 사는", mbtiText.value.toUpperCase(Locale.ROOT), it)
                    else -> FrontCard()
                }
            }
            if (fc != null) binding.cardview.getInstance(fc)
        }
    }

    companion object {
        private val characterList: HashMap<Int, Int> = hashMapOf(
            0 to R.drawable.ic_card_ghost,
            1 to R.drawable.ic_card_star,
            2 to R.drawable.ic_card_bear,
            3 to R.drawable.ic_card_raccon,
            4 to R.drawable.ic_card_cat,
            5 to R.drawable.ic_card_rabbit,
            6 to R.drawable.ic_card_fox,
            7 to R.drawable.ic_card_water,
            8 to R.drawable.ic_card_penguin,
            9 to R.drawable.ic_card_dog,
            10 to R.drawable.ic_card_mouse,
            11 to R.drawable.ic_card_panda
        )

        private const val COMMUNITY_WORKER = "직장인"
        private const val COMMUNITY_STUDENT = "학생"
        private const val COMMUNITY_TRAINEE = "취업준비생"
    }
}