package com.teumteum.teumteum.presentation.group.review

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.teumteum.base.BindingFragment
import com.teumteum.base.util.extension.defaultToast
import com.teumteum.base.util.extension.longArgs
import com.teumteum.base.util.extension.setOnSingleClickListener
import com.teumteum.base.util.extension.stringArgs
import com.teumteum.domain.entity.ReviewFriend
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.FragmentReviewFriendDetailBinding
import com.teumteum.teumteum.util.ResMapper
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ReviewFriendDetailFragment :
    BindingFragment<FragmentReviewFriendDetailBinding>(R.layout.fragment_review_friend_detail) {
    private val viewModel by activityViewModels<ReviewViewModel>()
    private val id by longArgs()
    private val characterId by longArgs()
    private val name by stringArgs()
    private val job by stringArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initClick()
        observe()
    }

    private fun initView() {
        with(binding) {
            tvTitle.text = getString(R.string.review_select_friend_title, name)
            tvName.text = name
            tvCompany.text = job
            ivUserIcon.setImageResource(ResMapper.getCharacterDrawableById(characterId.toInt()))
        }

        binding.btnReview.text =
            "리뷰 남기기 (${viewModel.currentFriendIndex}/${viewModel.selectFriendList.size}}"
    }

    private fun initClick() {
        val friendDetail = ReviewFriend(id, characterId, name, job)
        binding.btnReview.setOnSingleClickListener {
            viewModel.addSelectDetailFriendList(friendDetail)
            if (viewModel.currentFriendIndex < viewModel.selectFriendList.size) {
                (requireActivity() as? ReviewActivity)?.nextFriendDetailFragment()
            } else {
                viewModel.postRegisterReview()
            }
        }

        binding.clBad.setOnSingleClickListener {
            setSelectReview(it.id)
            friendDetail.review = "별로에요"
        }

        binding.clBest.setOnSingleClickListener {
            setSelectReview(it.id)
            friendDetail.review = "최고에요"
        }

        binding.clGood.setOnSingleClickListener {
            setSelectReview(it.id)
            friendDetail.review = "좋아요"
        }
    }

    private fun observe() {
        viewModel.reviewState.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                when (it) {
                    is ReviewUiState.Success -> {
                        startActivity(Intent(requireActivity(), ReviewFinishActivity::class.java))
                    }

                    is ReviewUiState.Failure -> {
                        requireActivity().defaultToast(it.msg)
                    }

                    else -> {}
                }

            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun setSelectReview(viewId: Int) {
        with(binding) {
            tvBad.isSelected = viewId == clBad.id
            ivBad.isSelected = viewId == clBad.id
            tvBest.isSelected = viewId == clBest.id
            ivBest.isSelected = viewId == clBest.id
            tvGood.isSelected = viewId == clGood.id
            ivGood.isSelected = viewId == clGood.id

            btnReview.isEnabled = true
        }
    }

    companion object {
        fun newInstance(id: Long, characterId: Long, name: String, job: String) =
            ReviewFriendDetailFragment().apply {
                arguments = Bundle().apply {
                    putLong("id", id)
                    putLong("characterId", characterId)
                    putString("name", name)
                    putString("job", job)
                }
            }
    }
}