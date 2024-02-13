package com.teumteum.teumteum.presentation.group.review

import android.content.res.Resources
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import com.teumteum.base.BindingDialogFragment
import com.teumteum.base.util.extension.setOnSingleClickListener
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.DialogReviewInfoBinding

class ReviewInfoDialog :
    BindingDialogFragment<DialogReviewInfoBinding>(R.layout.dialog_review_info) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initClick()
    }

    private fun initClick() {
        binding.btnBack.setOnSingleClickListener {
            dismiss()
        }

        binding.btnOk.setOnSingleClickListener {
            requireActivity().finish()
        }
    }

    private fun setDialogBackground() {
        val deviceWidth = Resources.getSystem().displayMetrics.widthPixels
        val dialogHorizontalMargin = (Resources.getSystem().displayMetrics.density * 26)

        dialog?.window?.apply {
            setLayout(
                (deviceWidth - dialogHorizontalMargin * 2).toInt(),
                WindowManager.LayoutParams.WRAP_CONTENT,
            )
            setBackgroundDrawableResource(R.drawable.shape_bg_dialog)
        }
        dialog?.setCancelable(true)
    }

    override fun onStart() {
        super.onStart()
        setDialogBackground()
    }
}