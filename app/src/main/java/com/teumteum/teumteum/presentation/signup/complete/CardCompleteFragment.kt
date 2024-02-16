package com.teumteum.teumteum.presentation.signup.complete

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.teumteum.base.BindingFragment
import com.teumteum.base.util.extension.setOnSingleClickListener
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.FragmentCardCompleteBinding
import com.teumteum.teumteum.presentation.signup.SignUpViewModel
import com.teumteum.teumteum.util.SignupUtils.CHARACTER_CARD_LIST
import com.teumteum.teumteum.util.SignupUtils.CHARACTER_CARD_LIST_BACK
import com.teumteum.teumteum.util.SignupUtils.STATUS_STUDENT
import com.teumteum.teumteum.util.SignupUtils.STATUS_TRAINEE
import com.teumteum.teumteum.util.SignupUtils.STATUS_WORKER
import com.teumteum.teumteum.util.custom.view.model.BackCard
import com.teumteum.teumteum.util.custom.view.model.FrontCard
import com.teumteum.teumteum.util.custom.view.model.Interest
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class CardCompleteFragment
    : BindingFragment<FragmentCardCompleteBinding>(R.layout.fragment_card_complete) {

    private val viewModel by activityViewModels<SignUpViewModel>()

    private lateinit var frontAnimation: AnimatorSet
    private lateinit var backAnimation: AnimatorSet
    private var isFront = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initCard()
        initCardAnim()
    }

    private fun initCard() {
        with(viewModel) {
            val fc = CHARACTER_CARD_LIST[characterId.value]?.let {
                when (community.value) {
                    STATUS_WORKER -> FrontCard(userName.value, "@${companyName.value}", jobDetailClass.value,
                        "lv.1층", "${preferredArea.value}에 사는", mbtiText.value.toUpperCase(Locale.ROOT), it)
                    STATUS_STUDENT -> FrontCard(userName.value, "@${schoolName.value}", readyJobDetailClass.value,
                        "lv.1층", "${preferredArea.value}에 사는", mbtiText.value.toUpperCase(Locale.ROOT), it)
                    STATUS_TRAINEE -> FrontCard(userName.value, "@${getText(R.string.signup_tv_card_trainee_job)}", readyJobDetailClass.value,
                        "lv.1층", "${preferredArea.value}에 사는", mbtiText.value.toUpperCase(Locale.ROOT), it)
                    else -> FrontCard()
                }
            }
            if (fc != null) binding.cardviewFront.getInstance(fc)

            val interests = mutableListOf<Interest>()
            for (i in interestField.value) {
                interests.add(Interest(i))
            }
            for (i in interestSelf.value) {
                interests.add(Interest(i))
            }

            val bc = BackCard(goalContent = goalText.value, characterResId = CHARACTER_CARD_LIST_BACK[characterId.value])
            binding.cardviewBack.getInstance(bc)
            binding.cardviewBack.apply {
                submitInterestList(interests)
                isModify = false
                setIsModifyDetail(false)
//                isModifyDetail = false
            }
        }
    }

    @SuppressLint("ResourceType")
    private fun initCardAnim() {
        val scale = resources.displayMetrics.density
        binding.cardviewFront.cameraDistance = 8000 * scale
        binding.cardviewBack.cameraDistance = 8000 * scale

        frontAnimation = AnimatorInflater.loadAnimator(requireContext(), com.teumteum.base.R.anim.card_reverse_front) as AnimatorSet
        backAnimation = AnimatorInflater.loadAnimator(requireContext(), com.teumteum.base.R.anim.card_reverse_back) as AnimatorSet

        binding.cardviewFront.setOnSingleClickListener {
            startAnim()
        }
        binding.cardviewBack.setOnSingleClickListener {
            startAnim()
        }
    }

    private fun startAnim() {
        isFront = if (isFront) {
            frontAnimation.setTarget(binding.cardviewFront)
            backAnimation.setTarget(binding.cardviewBack)
            frontAnimation.start()
            backAnimation.start()
            false
        } else {
            frontAnimation.setTarget(binding.cardviewBack)
            backAnimation.setTarget(binding.cardviewFront)
            backAnimation.start()
            frontAnimation.start()
            true
        }
    }

    companion object {
    }
}