import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlin.math.sqrt

class ShakeDetector(
    private val context: Context,
    private val onShake: (Context) -> Unit,
    private val onStop: () -> Unit
) : SensorEventListener {
    private var sensorManager: SensorManager =
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private var accelerometer: Sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
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

    override fun onSensorChanged(event: SensorEvent) {
        val curTime = System.currentTimeMillis()

        if ((curTime - lastUpdate) > 100) {
            val diffTime = (curTime - lastUpdate)
            lastUpdate = curTime

            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]

            val speed =
                sqrt((x - lastX) * (x - lastX) + (y - lastY) * (y - lastY) + (z - lastZ) * (z - lastZ)) / diffTime * 10000

            if (speed > SHAKE_THRESHOLD) {
                onShake(context)
                lastMovementTime = curTime
            }

            // 사용자가 멈추었는지 확인 (여기서는 2초 동안 움직임이 없으면 멈춤으로 간주)
            if ((curTime - lastMovementTime) > 2000) {
                onStop()
            }

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