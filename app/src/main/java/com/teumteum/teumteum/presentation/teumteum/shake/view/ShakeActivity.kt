package com.teumteum.teumteum.presentation.teumteum.shake.view

import ShakeDetector
import android.content.Context
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.constraintlayout.widget.ConstraintLayout
import com.teumteum.base.BindingActivity
import com.teumteum.base.component.appbar.AppBarLayout
import com.teumteum.base.component.appbar.AppBarMenu
import com.teumteum.base.databinding.LayoutCommonAppbarBinding
import com.teumteum.base.util.TransformUtils
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.ActivityShakeBinding
import com.teumteum.teumteum.presentation.teumteum.shake.model.UserInterest
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
    private val shakeViews = mutableListOf<ShakeView>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initAppBarLayout()

        shakeDetector = ShakeDetector(this, ::triggerVibration, ::stopVibration)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        addUserInterestView(12)
    }

    override fun initAppBarLayout() {
        setAppBarHeight(48)

        addMenuToLeft(
            AppBarMenu.IconStyle(
                resourceId = R.drawable.ic_arrow_left_l,
                useRippleEffect = false,
                clickEvent = null
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
            val amplitudes = IntArray(8) { 190 }

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
            shakeViews.forEach { userInterestView ->
                userInterestView.updateViewPositions(x, y)
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    private fun addUserInterestView(numberOfInterests: Int) {
        val shakeView = ShakeView(this)

        // 뷰의 폭과 높이를 dp 단위로 지정 (여기서는 100dp x 100dp)
        val viewWidth = TransformUtils.dpToPx(80f)  // dp를 픽셀로 변환
        val viewHeight = TransformUtils.dpToPx(80f) // dp를 픽셀로 변환

        // 지정된 수만큼 UserInterest 객체들을 생성하고 ShakeView에 추가
        for (i in 1..numberOfInterests) {
            // UserInterest의 위치를 무작위로 설정 (화면 경계 내에서)
            val x = Random.nextFloat() * (getScreenWidth() - viewWidth)
            val y = Random.nextFloat() * (getScreenHeight() - viewHeight)

            // UserInterest의 색상을 무작위로 설정
            val color = Color.argb(
                255,
                Random.nextInt(256),
                Random.nextInt(256),
                Random.nextInt(256)
            )

            // UserInterest의 움직임 민감도를 설정 (여기서는 무작위 값 사용)
            val moveSensitivity = Random.nextFloat() * 2f

            // UserInterest 객체 생성 및 추가
            val userInterest = UserInterest(
                x,
                y,
                viewWidth,
                viewHeight,
                color,
                moveSensitivity,
                "디자인"
            )
            shakeView.addUserInterest(userInterest)
        }

        // 생성된 ShakeView를 shakeViews 리스트에 추가
        shakeViews.add(shakeView)

        // ShakeView의 레이아웃 파라미터 설정 (MATCH_PARENT를 사용하여 전체 화면을 차지하도록 설정)
        val layoutParams = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.MATCH_PARENT,
            ConstraintLayout.LayoutParams.MATCH_PARENT
        )
        shakeView.layoutParams = layoutParams

        // ShakeView를 Fragment의 레이아웃에 추가
        binding.cl.addView(shakeView)
    }
}