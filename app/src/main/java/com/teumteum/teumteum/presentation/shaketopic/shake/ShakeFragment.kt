package com.teumteum.teumteum.presentation.shaketopic.shake

import ShakeDetector
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import com.teumteum.base.BindingFragment
import com.teumteum.base.component.appbar.AppBarLayout
import com.teumteum.base.component.appbar.AppBarMenu
import com.teumteum.base.databinding.LayoutCommonAppbarBinding
import com.teumteum.base.util.TransformUtils
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.FragmentShakeBinding
import com.teumteum.teumteum.presentation.familiar.FamiliarDialogActivity
import com.teumteum.teumteum.presentation.shaketopic.ShakeTopicActivity
import com.teumteum.teumteum.presentation.shaketopic.ShakeTopicViewModel
import com.teumteum.teumteum.presentation.shaketopic.shake.model.InterestViewConfig
import com.teumteum.teumteum.presentation.shaketopic.shake.model.InterestViewData
import com.teumteum.teumteum.util.AuthUtils
import com.teumteum.teumteum.util.ResMapper
import com.teumteum.teumteum.util.custom.uistate.UiState
import com.teumteum.teumteum.util.extension.getScreenHeight
import com.teumteum.teumteum.util.extension.getScreenWidth
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import kotlin.random.Random

@AndroidEntryPoint
class ShakeFragment :
    BindingFragment<FragmentShakeBinding>(R.layout.fragment_shake), AppBarLayout,
    SensorEventListener {

    private lateinit var shakeDetector: ShakeDetector
    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null
    private val interestViews = mutableListOf<InterestView>()
    private val viewModel by viewModels<ShakeTopicViewModel>({ requireActivity() })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupShakeDetector()
        setupSensorManager()
        processReceivedFriendList()
        initAppBarLayout()
    }

    private fun setupShakeDetector() {
        shakeDetector =
            ShakeDetector(
                requireActivity(),
                ::triggerVibration,
                ::stopVibration,
                ::onShakeStart, // 흔들기 시작 콜백
                ::onShakeComplete
            )
    }

    private fun onShakeStart() {
        getTopics() // 흔들기 시작과 동시에 호출
    }

    private fun onShakeComplete() {
        if (activity is ShakeTopicActivity) {
            (activity as ShakeTopicActivity).onShakeCompleted()
        }
    }

    private fun getTopics(){
        val myId = AuthUtils.getMyInfo(requireContext())?.id.toString()
        val userIds = viewModel.friends.value?.map { it.id.toString() }?.toMutableList()?.apply {
            add(myId)
        }
        userIds?.let { viewModel.getTopics(userIds = it) }
    }

    private fun setupSensorManager() {
        sensorManager = requireContext().getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    }

    private fun processReceivedFriendList() {
        val myInfo = AuthUtils.getMyInfo(requireActivity())
        val friends = viewModel.friends.value ?: listOf()

        // myInfo의 관심사 추출
        val myInterests = myInfo?.interests?.map { interest ->
            InterestViewData(
                interest,
                ResMapper.getColorByCharacterId(characterId = myInfo.characterId.toInt())
            )
        }

        // friends의 관심사 추출 및 결합
        val userInterests = mutableListOf<InterestViewData>().apply {
            addAll(myInterests ?: emptyList())

            for (friend in friends) {
                val color = ResMapper.getColorByCharacterId(characterId = friend.characterId)
                friend.interests.forEach { interest ->
                    add(InterestViewData(interest, color))
                }
            }
        }

        addUserInterestView(userInterests)

        val totalPeople = friends.size + 1
        val titleText = when (totalPeople) {
            2 -> resources.getString(R.string.shake_title_two)
            3 -> resources.getString(R.string.shake_title_three)
            4 -> resources.getString(R.string.shake_title_four)
            5 -> resources.getString(R.string.shake_title_five)
            else -> resources.getString(R.string.shake_title_two) //기본값
        }
        binding.tvShakeTitle.text = titleText
    }

    override fun initAppBarLayout() {
        setAppBarHeight(48)
        setAppBarBackgroundColor(com.teumteum.base.R.color.background)

        addMenuToLeft(
            AppBarMenu.IconStyle(
                resourceId = R.drawable.ic_arrow_left_l,
                useRippleEffect = false,
                clickEvent = ::startFamiliarDialogActivity
            )
        )
    }

    private fun startFamiliarDialogActivity() {
        val intent = Intent(requireContext(), FamiliarDialogActivity::class.java)
        startActivity(intent)
    }

    override val appBarBinding: LayoutCommonAppbarBinding
        get() = binding.appBar

    override fun onResume() {
        super.onResume()
        shakeDetector.resume()

        accelerometer?.let {
            sensorManager.registerListener(
                this,
                it,
                SensorManager.SENSOR_DELAY_NORMAL
            )
        }
    }

    override fun onPause() {
        super.onPause()
        shakeDetector.pause()
        sensorManager.unregisterListener(this)
    }

    fun triggerVibration(context: Context) {
        val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val vibrationPattern = LongArray(8) { if (it == 0) 0L else 1L }
            val amplitudes = IntArray(8) { 200 }

            val vibrationEffect = VibrationEffect.createWaveform(vibrationPattern, amplitudes, -1)
            vibrator.vibrate(vibrationEffect)
        } else {
            vibrator.vibrate(50)
        }
    }

    private fun stopVibration() {
        val vibrator = requireContext().getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        vibrator.cancel()
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            val x = -event.values[0] // X-axis data
            val y = event.values[1] // Y-axis data

            // Update positions of all UserInterestView objects
            interestViews.forEach { userInterestView ->
                userInterestView.updateViewPositions(x, y)
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    private fun addUserInterestView(userInterests: List<InterestViewData>) {
        val interestView = InterestView(requireActivity())

        // 지정된 수만큼 UserInterest 객체들을 생성하고 ShakeView에 추가
        userInterests.forEach { info ->
            val viewWidth = TransformUtils.dpToPx(80f)
            val viewHeight = TransformUtils.dpToPx(80f)
            val moveSensitivity = 60f +
                    Random.nextFloat() * 100f //애니메이션 duration과 moveSensitivity 밸런싱으로 부드러움 조절


            val interestViewConfig = InterestViewConfig(
                x = Random.nextFloat() * (requireActivity().getScreenWidth() - viewWidth),
                y = Random.nextFloat() * (requireActivity().getScreenHeight() - viewHeight),
                width = viewWidth,
                height = viewHeight,
                color = info.color, // 지정된 색상 사용,
                moveSensitivity = moveSensitivity,
                text = info.text // 지정된 텍스트 사용
            )
            interestView.addUserInterest(interestViewConfig)
        }

        interestViews.add(interestView)

        // ShakeView의 레이아웃 파라미터 설정 (MATCH_PARENT를 사용하여 전체 화면을 차지하도록 설정)
        val layoutParams = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.MATCH_PARENT,
            ConstraintLayout.LayoutParams.MATCH_PARENT
        )
        interestView.layoutParams = layoutParams

        // ShakeView를 Fragment의 레이아웃에 추가
        binding.clChild.addView(interestView)
    }
}