package com.teumteum.teumteum.presentation.teumteum

import ShakeDetector
import android.content.Context
import android.content.Context.SENSOR_SERVICE
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.constraintlayout.widget.ConstraintLayout
import com.teumteum.base.BindingFragment
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.FragmentTeumTeumBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TeumTeumFragment :
    BindingFragment<FragmentTeumTeumBinding>(R.layout.fragment_teum_teum), SensorEventListener {

    private lateinit var shakeDetector: ShakeDetector
    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null
    private var gyroscope: Sensor? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        shakeDetector = ShakeDetector(requireContext(), ::triggerVibration, ::stopVibration)

        sensorManager = requireContext().getSystemService(SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
    }

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
        gyroscope?.let {
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
            val amplitudes = IntArray(8) { 190 } // 8개의 요소를 모두 150으로 초기화

            val vibrationEffect =
                VibrationEffect.createWaveform(vibrationPattern, amplitudes, -1) // -1은 반복 없음을 의미
            vibrator.vibrate(vibrationEffect)
        } else {
            // API 26 미만에서는 강도 조절이 불가능하므로 간단한 진동 사용
            vibrator.vibrate(50)
        }
    }

    private fun stopVibration() {
        val vibrator = requireContext().getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        vibrator.cancel()
    }

    companion object {
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            val x = event.values[0] // X축 데이터
            val y = event.values[1] // Y축 데이터

            val layoutParams = binding.vDynamic.layoutParams as ConstraintLayout.LayoutParams

            // 센서 값에 따라 horizontalBias와 verticalBias를 조정
            // 단, 값은 0.0과 1.0 사이에 있어야 함
            val newHorizontalBias = Math.max(0f, Math.min(1f, layoutParams.horizontalBias - x * 0.01f))
            val newVerticalBias = Math.max(0f, Math.min(1f, layoutParams.verticalBias + y * 0.01f))

            layoutParams.horizontalBias = newHorizontalBias
            layoutParams.verticalBias = newVerticalBias
            binding.vDynamic.layoutParams = layoutParams
        }
        // 자이로스코프 센서 로직 추가 가능
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }
}