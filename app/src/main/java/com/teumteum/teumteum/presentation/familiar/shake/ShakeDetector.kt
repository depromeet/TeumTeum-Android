import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import timber.log.Timber
import kotlin.math.sqrt

class ShakeDetector(
    private val context: Context,
    private val onShake: (Context) -> Unit,
    private val onStop: () -> Unit,
    private val onShakeDurationExceeded: () -> Unit
) : SensorEventListener {
    private var sensorManager: SensorManager =
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private var accelerometer: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    private var lastUpdate: Long = 0
    private var lastMovementTime: Long = System.currentTimeMillis()
    private var lastX: Float = 0.0f
    private var lastY: Float = 0.0f
    private var lastZ: Float = 0.0f

    init {
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
    }

    //센서 이벤트 리스너가 등록된 센서의 정확도가 바뀔 때마다 실행
    //sensor : 정확도가 변경된 센서 객체
    //accuracy : 새로운 정확도 값
    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
    }

    private var cumulativeShakeTime: Long = 0 // 흔들림 누적 시간을 저장하는 변수
    override fun onSensorChanged(event: SensorEvent) {
        val curTime = System.currentTimeMillis()

        if ((curTime - lastUpdate) > 100) {
            val diffTime = (curTime - lastUpdate)
            lastUpdate = curTime

            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]

            val speed = sqrt((x - lastX) * (x - lastX) + (y - lastY) * (y - lastY) + (z - lastZ) * (z - lastZ)) / diffTime * 10000

            if (speed > SHAKE_THRESHOLD) {
                onShake(context)

                // Accumulate the shake duration
                cumulativeShakeTime += diffTime
                Timber.tag("누적 흔들기 시작").d("$cumulativeShakeTime ms")

                // Check if cumulative shake duration exceeds 3 seconds (3000 milliseconds)
                if (cumulativeShakeTime >= 3000) {
                    onShakeDurationExceeded() // Trigger the action when duration exceeds
                    cumulativeShakeTime = 0 // Reset the shake duration ONLY when the action is triggered
                }

                lastMovementTime = curTime
            }

            // Do not reset cumulativeShakeTime here
            // Else block removed

            lastX = x
            lastY = y
            lastZ = z
        }
    }

    fun resume() {
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
    }

    fun pause() {
        sensorManager.unregisterListener(this)
    }

    companion object {
        private const val SHAKE_THRESHOLD = 600 // 흔들림 감지 임계값
    }
}