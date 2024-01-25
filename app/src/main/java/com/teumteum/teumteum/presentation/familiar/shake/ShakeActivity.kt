package com.teumteum.teumteum.presentation.familiar.shake

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
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.teumteum.base.BindingActivity
import com.teumteum.base.R.color
import com.teumteum.base.component.appbar.AppBarLayout
import com.teumteum.base.component.appbar.AppBarMenu
import com.teumteum.base.databinding.LayoutCommonAppbarBinding
import com.teumteum.base.util.TransformUtils
import com.teumteum.domain.entity.Friend
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.ActivityShakeBinding
import com.teumteum.teumteum.presentation.familiar.introduce.IntroduceActivity.Companion.EXTRA_FRIENDS
import com.teumteum.teumteum.presentation.familiar.shake.model.InterestViewConfig
import com.teumteum.teumteum.presentation.familiar.shake.model.InterestViewData
import com.teumteum.teumteum.presentation.familiar.topic.TopicActivity
import com.teumteum.teumteum.util.IdMapper
import com.teumteum.teumteum.util.extension.getScreenHeight
import com.teumteum.teumteum.util.extension.getScreenWidth
import dagger.hilt.android.AndroidEntryPoint
import kotlin.random.Random

@AndroidEntryPoint
class ShakeActivity : BindingActivity<ActivityShakeBinding>(R.layout.activity_shake), AppBarLayout,
    SensorEventListener {

    private lateinit var shakeDetector: ShakeDetector
    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null
    private val interestViews = mutableListOf<InterestView>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initAppBarLayout()
        setupShakeDetector()
        setupSensorManager()
        processReceivedFriendList()
    }

    private fun setupShakeDetector() {
        shakeDetector = ShakeDetector(this, ::triggerVibration, ::stopVibration, ::startTopicActivity)
    }

    private fun setupSensorManager() {
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    }

    private fun processReceivedFriendList() {
        val friends = intent.getSerializableExtra(EXTRA_FRIENDS) as? List<Friend> ?: listOf()
        val userInterests = extractInterests(friends)
        addUserInterestView(userInterests)
    }

    private fun extractInterests(friends: List<Friend>): List<InterestViewData> {
        val interestsData = mutableListOf<InterestViewData>()
        for (friend in friends) {
            val color = IdMapper.getColorByCharacterId(characterId = friend.characterId)
            friend.interests.forEach { interest ->
                interestsData.add(InterestViewData(interest, color))
            }
        }
        return interestsData
    }

    private fun startTopicActivity() {
        startActivity(Intent(this, TopicActivity::class.java))
    }

    override fun initAppBarLayout() {
        setAppBarHeight(48)
        setAppBarBackgroundColor(color.background)

        addMenuToLeft(
            AppBarMenu.IconStyle(
                resourceId = R.drawable.ic_arrow_left_l,
                useRippleEffect = false,
                clickEvent = ::finish
            )
        )
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
        val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
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
        val interestView = InterestView(this)

        // 지정된 수만큼 UserInterest 객체들을 생성하고 ShakeView에 추가
        userInterests.forEach { info ->
            val viewWidth = TransformUtils.dpToPx(80f)
            val viewHeight = TransformUtils.dpToPx(80f)
            val moveSensitivity =
                Random.nextFloat() * 100f //애니메이션 duration과 moveSensitivity 밸런싱으로 부드러움 조절


            val interestViewConfig = InterestViewConfig(
                x = Random.nextFloat() * (getScreenWidth() - viewWidth),
                y = Random.nextFloat() * (getScreenHeight() - viewHeight),
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