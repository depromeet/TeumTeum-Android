package com.teumteum.teumteum.presentation.signup.modal

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.BottomsheetSingleModalBinding

class SingleModalBottomSheet : BottomSheetDialogFragment() {

    private lateinit var binding: BottomsheetSingleModalBinding
    private lateinit var singleRvAdapter: SingleModalAdapter
    private lateinit var itemClickListener: (String) -> Unit
    private lateinit var focusedShowImageView: ImageView
    private var selectedItem: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomsheetSingleModalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setStyle(STYLE_NORMAL, com.teumteum.base.R.style.CustomBottomSheetDialogTheme)
        initSingleRvAdapter()
        initBottomSheetArguments()
    }

    private fun initSingleRvAdapter() {
        singleRvAdapter = SingleModalAdapter(itemClickListener)
        singleRvAdapter.setSelectedItem(selectedItem)
        with(binding.rv) {
            adapter = singleRvAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun initBottomSheetArguments() {
        arguments?.let {
            val title = it.getString("title", "Default Title")
            binding.tvTitle.text = title
        }

        arguments?.getStringArrayList("data")?.let { dataList ->
            singleRvAdapter.submitList(dataList)
        }
    }

    fun setFocusedImageView(iv: ImageView) {
        focusedShowImageView = iv
    }

    fun setSelectedItem(item: String) {
        selectedItem = item
    }

    override fun onDismiss(dialog: DialogInterface) {
        focusedShowImageView.setImageResource(R.drawable.ic_arrow_down_l)
        super.onDismiss(dialog)
    }

    companion object {
        const val TAG = "SingleModal"

        fun newInstance(title: String, dataList: ArrayList<String>, itemClickListener: (String) -> Unit)
            : SingleModalBottomSheet {
            return SingleModalBottomSheet().apply {
                arguments = Bundle().apply {
                    putString("title", title)
                    putStringArrayList("data", dataList)
                }
                this.itemClickListener = itemClickListener
            }
        }
    }
}