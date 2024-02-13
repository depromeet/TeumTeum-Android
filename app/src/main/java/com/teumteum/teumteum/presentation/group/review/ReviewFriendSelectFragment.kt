package com.teumteum.teumteum.presentation.group.review

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.teumteum.base.BindingFragment
import com.teumteum.base.util.extension.setOnSingleClickListener
import com.teumteum.domain.entity.ReviewFriend
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.FragmentReviewFriendSelectBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


class ReviewFriendSelectFragment: BindingFragment<FragmentReviewFriendSelectBinding>(R.layout.fragment_review_friend_select) {
    private val viewModel by activityViewModels<ReviewViewModel>()
    private var adapter: ReviewFriendListAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initEvent()
        observe()
    }

    private fun initView() {
        adapter = ReviewFriendListAdapter { friend ->
            val currentList = Gson().toJson(adapter?.currentList)
            val newCurrentList = Gson().fromJson<List<ReviewFriend>>(currentList, object : TypeToken<List<ReviewFriend>>() {}.type)

            newCurrentList?.forEach {
                if(it.id == friend.id) {
                    it.isSelected = !it.isSelected
                }
            }
            adapter?.submitList(newCurrentList)
            binding.btnReview.isEnabled = newCurrentList.any { it.isSelected }
        }
        binding.rvUser.run {
            itemAnimator = null
            adapter = this@ReviewFriendSelectFragment.adapter
        }
        viewModel.getReviewFriendList()
    }

    private fun initEvent() {
        binding.btnReview.setOnSingleClickListener {
            val selectFriendList = adapter?.currentList?.filter {
                it.isSelected
            }
            selectFriendList?.let {
                viewModel.setSelectFriendList(it)
            }

            (requireActivity() as? ReviewActivity)?.nextFriendDetailFragment()
        }
    }

    private fun observe() {
        viewModel.moimFriendList.flowWithLifecycle(lifecycle)
            .onEach {
                if (it.isNotEmpty()) {
                    adapter?.submitList(it)
                }
            }.launchIn(lifecycleScope)
    }

    override fun onDestroyView() {
        adapter = null
        super.onDestroyView()
    }
}