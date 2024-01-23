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
import com.teumteum.teumteum.databinding.BottomsheetAreaModalBinding
import com.teumteum.teumteum.databinding.BottomsheetSingleModalBinding

class SingleModalBottomSheet : BottomSheetDialogFragment() {

    private var _binding: BottomsheetSingleModalBinding? = null
    private val binding get() = _binding!!

    private lateinit var singleRvAdapter: SingleModalAdapter
    private lateinit var itemClickListener: (String) -> Unit
    private var focusedShowImageView: ImageView? = null
    private var selectedItem: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomsheetSingleModalBinding.inflate(inflater, container, false)
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

    fun updateList(newList: List<String>) {
        if(this::singleRvAdapter.isInitialized) {
            singleRvAdapter.submitList(newList)
            singleRvAdapter.notifyDataSetChanged()
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
