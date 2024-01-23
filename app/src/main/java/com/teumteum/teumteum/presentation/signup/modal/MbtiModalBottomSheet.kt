package com.teumteum.teumteum.presentation.signup.modal

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
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
        Log.d("teum-mbti", "setFeeler: $feeler")
        with(binding) {
            btnF.isSelected = feeler
            btnT.isSelected = !feeler
        }
        Log.d("teum-mbti", "check: ${binding.btnF.isSelected} ${binding.btnT.isSelected}")
        mbtiText[2] = if (feeler) 'F' else 'T'
    }

    private fun setPerceiver(perceiver: Boolean) {
        with(binding) {
            btnP.isSelected = perceiver
            btnJ.isSelected = !perceiver
        }
        mbtiText[3] = if (perceiver) 'P' else 'J'
    }

    fun initMbti() {
        mbtiText = "xxxx".toCharArray()
    }

    private fun initListener() {
        with(binding) {
            btnNext.setOnClickListener {
                itemClickListener.invoke(mbtiText.concatToString())
            }
            btnE.setOnClickListener {
                setExtrovert(true)
                checkValid()
            }
            btnI.setOnClickListener {
                setExtrovert(false)
                checkValid()
            }
            btnN.setOnClickListener {
                setIntuitive(true)
                checkValid()
            }
            btnS.setOnClickListener {
                setIntuitive(false)
                checkValid()
            }
            btnF.setOnClickListener {
                setFeeler(true)
                checkValid()
            }
            btnT.setOnClickListener {
                setFeeler(false)
                checkValid()
            }
            btnP.setOnClickListener {
                setPerceiver(true)
                checkValid()
            }
            btnJ.setOnClickListener {
                setPerceiver(false)
                checkValid()
            }
        }
    }

    private fun checkValid() {
        binding.btnNext.isEnabled = !mbtiText.contains('x')
    }

    fun setFocusedImageView(iv: ImageView) {
        focusedShowImageView = iv
    }

    fun setSelectedMbti(extrovert: Boolean, intuitive: Boolean, feeler: Boolean, perceiver: Boolean) {
        binding.root.post {
            with(binding) {
                if (extrovert) btnE.callOnClick() else btnI.callOnClick()
                if (intuitive) btnN.callOnClick() else btnS.callOnClick()
                if (feeler) btnF.callOnClick() else btnT.callOnClick()
                if (perceiver) btnP.callOnClick() else btnJ.callOnClick()
            }
        }
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