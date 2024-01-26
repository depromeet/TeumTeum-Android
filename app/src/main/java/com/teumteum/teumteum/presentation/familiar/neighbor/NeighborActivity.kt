package com.teumteum.teumteum.presentation.familiar.neighbor

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Looper
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.teumteum.base.BindingActivity
import com.teumteum.base.component.appbar.AppBarLayout
import com.teumteum.base.component.appbar.AppBarMenu
import com.teumteum.base.databinding.LayoutCommonAppbarBinding
import com.teumteum.base.util.extension.setOnSingleClickListener
import com.teumteum.domain.repository.RequestPostNeighborUser
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.ActivityNeighborBinding
import com.teumteum.teumteum.presentation.familiar.introduce.IntroduceActivity
import com.teumteum.teumteum.util.AuthUtils
import com.teumteum.teumteum.util.IdMapper
import com.teumteum.teumteum.util.custom.uistate.UiState
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class NeighborActivity : BindingActivity<ActivityNeighborBinding>(R.layout.activity_neighbor),
    AppBarLayout {
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private val viewModel by viewModels<NeighborViewModel>()
    private val selectedNeighborIds = mutableListOf<Long>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initAppBarLayout()
        initMyCharacter()
        setUpListener()
        setUpObserver()
    }

    override val appBarBinding: LayoutCommonAppbarBinding
        get() = binding.appBar

    override fun initAppBarLayout() {
        setAppBarHeight(48)

        setAppBarBackgroundColor(com.teumteum.base.R.color.background)
        addMenuToLeft(
            AppBarMenu.IconStyle(
                resourceId = R.drawable.ic_arrow_left_l,
                useRippleEffect = false,
                clickEvent = {}
            )
        )
    }

    private fun initMyCharacter() {
        val myInfo = AuthUtils.getMyInfo(this)
        if (myInfo != null) {
            val imageRes = IdMapper.getCharacterDrawableById(myInfo.characterId.toInt())
            setCharacterView(
                view = binding.cvMe,
                imageRes = imageRes,
                job = myInfo.job.jobClass,
                name = myInfo.name
            ).apply {
                binding.cvMe.isEnabled = false //내 캐릭터 터치 시 체크박스 visible 방지
            }
        }
    }


    private fun setUpListener() {
        binding.btnStart.setOnSingleClickListener {
            startIntroduceActivity()
        }
    }

    private fun setUpObserver() {
        viewModel.neighborUserState.observe(this) { state ->
            when (state) {
                UiState.Loading -> {}
                UiState.Success -> {
                    val neighbors = viewModel.neighborUsers
                    val views =
                        listOf(binding.cv1, binding.cv2, binding.cv3, binding.cv4, binding.cv5)

                    neighbors.forEachIndexed { index, neighbor ->
                        if (index < views.size) {
                            val view = views[index]

                            view.setOnSingleClickListener {
                                view.isCharacterSelected =
                                    !view.isCharacterSelected //체크박스 visibility

                                val id = neighbor.id
                                if (selectedNeighborIds.contains(id)) {
                                    selectedNeighborIds.remove(id)
                                } else {
                                    selectedNeighborIds.add(id)
                                }
                            }
                            val imageRes =
                                IdMapper.getCharacterDrawableById(neighbor.characterId.toInt())
                            setCharacterView(
                                view = view,
                                imageRes = imageRes,
                                job = neighbor.jobDetailClass,
                                name = neighbor.name
                            )
                        }
                    }
                }

                UiState.Failure -> {}
                else -> {}
            }
        }
    }

    private fun setCharacterView(view: CharacterView, imageRes: Int, job: String, name: String) {
        with(view) {
            characterImage.setImageResource(imageRes)
            characterJob.text = job
            characterName.text = name
            isVisible = true // todo - 제거 고민중
        }
    }

    private fun startIntroduceActivity() {
        val idsString = selectedNeighborIds.joinToString(separator = ",")
        val intent = Intent(this, IntroduceActivity::class.java).apply {
            putExtra(EXTRA_NEIGHBORS_IDS, idsString)
        }
        startActivity(intent)
    }


    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        val locationRequest = LocationRequest.create().apply {
            interval = 1000
            fastestInterval = 1000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                for (location in locationResult.locations) {
                    Timber.tag("Location")
                        .d("${location.latitude}, ${location.longitude}") //todo - post함수 추가
                    postNeighborUser(
                        latitude = location.latitude,
                        longitude = location.longitude
                    ) //todo - 테스트용 임시 좌표, 동적 좌표로 수정 필요
                }
            }
        }

        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    private fun postNeighborUser(latitude: Double, longitude: Double) { //todo - 함수명 수정
        val myInfo = AuthUtils.getMyInfo(this)
        Timber.tag("getMyInfo").d("$myInfo")

        if (myInfo != null) {
            viewModel.postNeighborUser(
                requestPostNeighborUser = RequestPostNeighborUser(
                    id = myInfo.id,
                    latitude = latitude,
                    longitude = longitude,
                    name = myInfo.name,
                    jobDetailClass = myInfo.job.detailClass,
                    characterId = myInfo.characterId
                )
            )
        }
    }

    override fun onResume() {
        super.onResume()
        startLocationUpdates()
    }

    override fun onPause() {
        super.onPause()
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }

    companion object {
        const val EXTRA_NEIGHBORS_IDS = "EXTRA_NEIGHBORS_IDS"
    }
}