package com.teumteum.teumteum.presentation.group.review

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.teumteum.base.BindingFragment
import com.teumteum.base.util.extension.longArgs
import com.teumteum.base.util.extension.longExtra
import com.teumteum.base.util.extension.setOnSingleClickListener
import com.teumteum.base.util.extension.stringArgs
import com.teumteum.domain.entity.ReviewFriend
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.FragmentReviewFriendDetailBinding
import com.teumteum.teumteum.util.ResMapper

class ReviewFriendDetailFragment: BindingFragment<FragmentReviewFriendDetailBinding>(R.layout.fragment_review_friend_detail) {
    private val viewModel by activityViewModels<ReviewViewModel>()
    private val id by longArgs()
    private val characterId by longArgs()
    private val name by stringArgs()
    private val job by stringArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initClick()
    }

    private fun initView() {
        with(binding) {
            tvTitle.text = getString(R.string.review_select_friend_title, name)
            tvName.text = name
            tvCompany.text = job
            ivUserIcon.setImageResource(ResMapper.getCharacterDrawableById(characterId.toInt()))
        }
    }

    private fun initClick() {
        val friendDetail = ReviewFriend(id, characterId, name, job)
        binding.btnReview.setOnSingleClickListener {
            if (viewModel.currentFriendIndex < viewModel.selectFriendList.size) {
                viewModel.addSelectDetailFriendList(friendDetail)
                (requireActivity() as? ReviewActivity)?.nextFriendDetailFragment()
            } else {

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