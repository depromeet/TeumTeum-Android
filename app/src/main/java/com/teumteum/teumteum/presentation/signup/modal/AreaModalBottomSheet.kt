package com.teumteum.teumteum.presentation.signup.modal

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.BottomsheetAreaModalBinding

class AreaModalBottomSheet : BottomSheetDialogFragment() {

    private lateinit var binding: BottomsheetAreaModalBinding
    private lateinit var cityRvAdapter: AreaCityModalAdapter
    private lateinit var streetRvAdapter: AreaStreetModalAdapter
    private lateinit var cityItemClickListener: (String) -> Unit
    private lateinit var streetItemClickListener: (String) -> Unit
    private lateinit var focusedShowImageView: ImageView
    private var selectedCity: String = ""
    private var selectedStreet: String = ""
    private var focusedCity: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomsheetAreaModalBinding.inflate(inflater, container, false)
        setModalBehavior()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setStyle(STYLE_NORMAL, com.teumteum.base.R.style.CustomBottomSheetDialogTheme)
        initCityRvAdapter()
        initStreetRvAdapter()
        getBottomSheetTitle()
    }

    /* 바텀 시트 STATE_EXPANDED로 고정 */
    private fun setModalBehavior() {
        val behavior = BottomSheetBehavior.from(binding.constraintLayoutArea)
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    /* 도시 (서울, 경기, 인천) 리사이클러뷰 초기화 */
    private fun initCityRvAdapter() {
        cityRvAdapter = AreaCityModalAdapter(cityItemClickListener)
        cityRvAdapter.submitList(resources.getStringArray(R.array.main_areas).toList())
        cityRvAdapter.setFocusedCity(selectedCity)
        with(binding.rvArea) {
            adapter = cityRvAdapter
            layoutManager = LinearLayoutManager(requireContext())
            isNestedScrollingEnabled = false
        }
    }

    /* 세부 도시 (용산, 마포 등) 리사이클러뷰 초기화 */
    private fun initStreetRvAdapter() {
        streetRvAdapter = AreaStreetModalAdapter(streetItemClickListener)
        streetRvAdapter.setSelectedStreet(selectedCity, selectedStreet)
        if (selectedCity.isNotBlank()) setFocusedCity(selectedCity)
        else setFocusedCity("서울")
        with(binding.rvDetailArea) {
            adapter = streetRvAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    /* 바텀시트 타이틀 지정 */
    private fun getBottomSheetTitle() {
        arguments?.let {
            val title = it.getString("title", "Default Title")
            binding.tvTitle.text = title
        }
    }

    /* 현재 입력창 우측 화살표 up & down을 위한 이미지뷰 저장 */
    fun setFocusedImageView(iv: ImageView) {
        focusedShowImageView = iv
    }

    /* 선택한 세부 도시 업데이트 */
    fun setSelectedStreet(city: String, street: String) {
        selectedStreet = street
        selectedCity = city
    }

    /* 현재 탐색 중인 (선택 X) 도시명 업데이트 */
    fun setFocusedCity(city: String) {
        focusedCity = city
        cityRvAdapter.setFocusedCity(focusedCity)
        when (focusedCity) {
            "서울" -> streetRvAdapter.submitList(resources.getStringArray(R.array.seoul_areas).toList())
            "경기" -> streetRvAdapter.submitList(resources.getStringArray(R.array.gyeonggi_areas).toList())
            "인천" -> streetRvAdapter.submitList(resources.getStringArray(R.array.incheon_areas).toList())
        }
        streetRvAdapter.setFocusedCity(focusedCity)
    }

    override fun onDismiss(dialog: DialogInterface) {
        focusedShowImageView.setImageResource(R.drawable.ic_arrow_down_l)
        super.onDismiss(dialog)
    }

    companion object {
        const val TAG = "AreaModal"

        fun newInstance(title: String, cityItemClickListener: (String) -> Unit, streetItemClickListener: (String) -> Unit)
                : AreaModalBottomSheet {
            return AreaModalBottomSheet().apply {
                arguments = Bundle().apply {
                    putString("title", title)
                }
                this.cityItemClickListener = cityItemClickListener
                this.streetItemClickListener = streetItemClickListener
            }
        }
    }
}
