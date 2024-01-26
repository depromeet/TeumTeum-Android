package com.teumteum.teumteum.presentation.signup.modal

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.teumteum.base.util.extension.setOnSingleClickListener
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.BottomsheetMbtiModalBinding

class MbtiModalBottomSheet : BottomSheetDialogFragment() {

    private var _binding: BottomsheetMbtiModalBinding? = null
    private val binding get() = _binding!!

    private lateinit var itemClickListener: (String) -> Unit
    private var focusedShowImageView: ImageView? = null
    private var mbtiText: CharArray = "xxxx".toCharArray()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BottomsheetMbtiModalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setStyle(STYLE_NORMAL, com.teumteum.base.R.style.CustomBottomSheetDialogTheme)
        initListener()
    }

    private fun setExtrovert(extrovert: Boolean) {
        with(binding) {
            btnE.isSelected = extrovert
            btnI.isSelected = !extrovert
        }
        mbtiText[0] = if (extrovert) 'E' else 'I'
    }

    private fun setIntuitive(intuitive: Boolean) {
        with(binding) {
            btnN.isSelected = intuitive
            btnS.isSelected = !intuitive
        }
        mbtiText[1] = if (intuitive) 'N' else 'S'
    }

    private fun setFeeler(feeler: Boolean) {
        with(binding) {
            btnF.isSelected = feeler
            btnT.isSelected = !feeler
        }
        mbtiText[2] = if (feeler) 'F' else 'T'
    }

    private fun setPerceiver(perceiver: Boolean) {
        with(binding) {
            btnP.isSelected = perceiver
            btnJ.isSelected = !perceiver
        }
        mbtiText[3] = if (perceiver) 'P' else 'J'
    }

    fun initDefaultMbti() {
        mbtiText = "xxxx".toCharArray()
    }

    private fun initListener() {
        with(binding) {
            btnNext.setOnSingleClickListener {
                itemClickListener.invoke(mbtiText.concatToString())
            }
            btnE.setOnSingleClickListener {
                setExtrovert(true)
                checkValid()
            }
            btnI.setOnSingleClickListener {
                setExtrovert(false)
                checkValid()
            }
            btnN.setOnSingleClickListener {
                setIntuitive(true)
                checkValid()
            }
            btnS.setOnSingleClickListener {
                setIntuitive(false)
                checkValid()
            }
            btnF.setOnSingleClickListener {
                setFeeler(true)
                checkValid()
            }
            btnT.setOnSingleClickListener {
                setFeeler(false)
                checkValid()
            }
            btnP.setOnSingleClickListener {
                setPerceiver(true)
                checkValid()
            }
            btnJ.setOnSingleClickListener {
                setPerceiver(false)
                checkValid()
            }

            when (mbtiText[0]) {
                'E' -> btnE.callOnClick()
                'I' -> btnI.callOnClick()
                else -> {}
            }
            when (mbtiText[1]) {
                'N' -> btnN.callOnClick()
                'S' -> btnS.callOnClick()
                else -> {}
            }
            when (mbtiText[2]) {
                'F' -> btnF.callOnClick()
                'T' -> btnT.callOnClick()
                else -> {}
            }
            when (mbtiText[3]) {
                'P' -> btnP.callOnClick()
                'J' -> btnJ.callOnClick()
                else -> {}
            }
        }
    }

    private fun checkValid() {
        binding.btnNext.isEnabled = !mbtiText.contains('x')
    }

    fun setFocusedImageView(iv: ImageView) {
        focusedShowImageView = iv
    }

    fun initSelectedMbti(extrovert: Boolean, intuitive: Boolean, feeler: Boolean, perceiver: Boolean) {
        mbtiText[0] = if (extrovert) 'E' else 'I'
        mbtiText[1] = if (intuitive) 'N' else 'S'
        mbtiText[2] = if (feeler) 'F' else 'T'
        mbtiText[3] = if (perceiver) 'P' else 'J'
    }

    override fun onDismiss(dialog: DialogInterface) {
        focusedShowImageView?.setImageResource(R.drawable.ic_arrow_down_l)
        super.onDismiss(dialog)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        const val TAG = "MbtiModal"

        fun newInstance(title: String, itemClickListener: (String) -> Unit)
                : MbtiModalBottomSheet {
            return MbtiModalBottomSheet().apply {
                arguments = Bundle().apply {
                    putString("title", title)
                }
                this.itemClickListener = itemClickListener
            }
        }
    }
}