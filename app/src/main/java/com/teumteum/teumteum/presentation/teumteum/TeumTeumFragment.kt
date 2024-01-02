package com.teumteum.teumteum.presentation.teumteum

import ShakeDetector
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import com.teumteum.base.BindingFragment
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.FragmentTeumTeumBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TeumTeumFragment :
    BindingFragment<FragmentTeumTeumBinding>(R.layout.fragment_teum_teum) {

    private lateinit var shakeDetector: ShakeDetector
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        shakeDetector = ShakeDetector(requireContext(), ::triggerVibration, ::stopVibration)
    }

    override fun onResume() {
        super.onResume()
        shakeDetector.resume()
    }

    override fun onPause() {
        super.onPause()
        shakeDetector.pause()
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
}