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
import com.teumteum.domain.repository.RequestPostNeighborUser
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.ActivityNeighborBinding
import com.teumteum.teumteum.presentation.familiar.introduce.IntroduceActivity
import com.teumteum.teumteum.util.AuthUtils
import com.teumteum.teumteum.util.DrawableMapper
import com.teumteum.teumteum.util.custom.uistate.UiState
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class NeighborActivity : BindingActivity<ActivityNeighborBinding>(R.layout.activity_neighbor),
    AppBarLayout {
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private val viewModel by viewModels<NeighborViewModel>()

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
            with(binding.cvMe) {
                characterImage.setImageResource(DrawableMapper.getCharacterDrawableById(myInfo.characterId.toInt()))
                characterName.text = myInfo.name
                characterJob.text = myInfo.job.name
                isVisible = true
            }
        }
    }

    private fun setUpListener() {
        binding.btnStart.setOnClickListener {
            startIntroduceActivity()
        }
    }

    private fun setUpObserver() { //한 바인딩 되면 페이지를 나갔다 들어올 때까지 계속 유지됨. 대응 조치 필요. List 전체를 다루는 게 아니라 어차피 최대 6명만 내려지니까 이 각각을 옵저빙하고 UI 상태 관리도
        //각각 해줘야 함.
        viewModel.neighborUserState.observe(this) { state ->
            when (state) {
                UiState.Loading -> {}
                UiState.Success -> {
                    val neighbors = viewModel.neighborUsers
                    val views =
                        listOf(binding.cv1, binding.cv2, binding.cv3, binding.cv4, binding.cv5)

                    neighbors.forEachIndexed { index, neighbor ->
                        if (index < views.size) {
                            val imageRes =
                                DrawableMapper.getCharacterDrawableById(neighbor.characterId.toInt())
                            setCharacterView(
                                view = views[index],
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
        startActivity(Intent(this, IntroduceActivity::class.java))
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
                    postNeighborUser(latitude = location.latitude, longitude = location.longitude) //todo - 테스트용 임시 좌표, 동적 좌표로 수정 필요
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
}