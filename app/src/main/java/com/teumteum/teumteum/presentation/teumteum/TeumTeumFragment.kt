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
import android.view.View
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

            // 뷰들의 초기 위치 설정
            val initialBiasX = 0.5f
            val initialBiasY = 0.5f

            // 센서 데이터에 적용되는 계수를 조정
            val movementScale = 0.05f // 움직임의 범위를 조정하는 계수

            // 각 뷰의 위치를 순차적으로 조정
            updateStackedViewPosition(binding.vDynamic1, x, y, initialBiasX, initialBiasY, movementScale+0.08f)
            updateStackedViewPosition(binding.vDynamic2, x, y, initialBiasX + 0.14f, initialBiasY + 0.35f, movementScale+0.1f)
            updateStackedViewPosition(binding.vDynamic3, x, y, initialBiasX + 0.28f, initialBiasY + 0.2f, movementScale+0.2f)
        }
    }

    private fun updateStackedViewPosition(view: View, x: Float, y: Float, biasX: Float, biasY: Float, scale: Float) {
        val layoutParams = view.layoutParams as ConstraintLayout.LayoutParams
        layoutParams.horizontalBias = Math.max(0f, Math.min(1f, biasX - x * scale))
        layoutParams.verticalBias = Math.max(0f, Math.min(1f, biasY + y * scale))
        view.layoutParams = layoutParams
    }
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }
}