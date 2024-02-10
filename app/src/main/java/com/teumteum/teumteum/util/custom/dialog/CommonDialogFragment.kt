package com.teumteum.teumteum.util.custom.dialog

import android.os.Bundle
import android.view.View
import com.teumteum.base.BindingDialogFragment
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.FragmentCommonDialogBinding
import com.teumteum.teumteum.util.extension.getCompatibleParcelableExtra


class CommonDialogFragment
    : BindingDialogFragment<FragmentCommonDialogBinding>(R.layout.fragment_common_dialog) {
    private var onNegativeButtonClicked: (() -> Unit)? = null
    private var onPositiveButtonClicked: () -> Unit = {}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.setCanceledOnTouchOutside(false)
        initDialogConfig()
        initNegativeButtonClickListener()
        initPositiveButtonClickListener()
    }

    private fun initDialogConfig() {
        val dialogConfig: CommonDialogConfig? =
            arguments?.getCompatibleParcelableExtra(ARG_COMMON_DIALOG)

        if (dialogConfig?.image == null) {
            binding.ivCommon.visibility = View.GONE
        }

        if (dialogConfig?.description.isNullOrEmpty()) {
            binding.tvCommonDescription.visibility = View.GONE
        }

        if (dialogConfig?.negativeButtonText.isNullOrEmpty()) {
            binding.btnCommonDialogNegative.visibility = View.GONE
        }

        binding.dialogConfig = dialogConfig
    }

    private fun initNegativeButtonClickListener() {
        binding.btnCommonDialogNegative.setOnClickListener {
            onNegativeButtonClicked?.invoke()
            dismiss()
        }
    }

    private fun initPositiveButtonClickListener() {
        binding.btnCommonDialogPositive.setOnClickListener {
            onPositiveButtonClicked.invoke()
            dismiss()
        }
    }

    companion object {
        private const val ARG_COMMON_DIALOG = "common_dialog_params"

        @JvmStatic
        fun newInstance(
            commonDialogConfig: CommonDialogConfig,
            onNegativeButtonClicked: (() -> Unit)? = null,
            onPositiveButtonClicked: () -> Unit,
        ) = CommonDialogFragment().apply {
            arguments = Bundle().apply {
                putParcelable(ARG_COMMON_DIALOG, commonDialogConfig)
            }
            this.onNegativeButtonClicked = onNegativeButtonClicked
            this.onPositiveButtonClicked = onPositiveButtonClicked
        }
    }
}
