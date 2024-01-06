package com.teumteum.teumteum.presentation.teumteum.shake.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.teumteum.base.util.TransformUtils
import com.teumteum.teumteum.R
import com.teumteum.teumteum.presentation.teumteum.shake.model.UserInterest
import com.teumteum.teumteum.util.extension.getScreenHeight
import com.teumteum.teumteum.util.extension.getScreenWidth
import java.lang.Float.max
import java.lang.Float.min

class ShakeView(
    context: Context,
    attrs: AttributeSet? = null,
) : View(context, attrs) {
    private val paint = Paint()
    private val views = mutableListOf<UserInterest>()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // Draw each view with rounded corners
        views.forEach { view ->
            paint.color = view.color
            // 반지름(radius) 값 정의
            val radius = TransformUtils.dpToPx(4f).toFloat() // 이 값을 변경하여 둥근 모서리의 크기를 조절할 수 있습니다.
            canvas.drawRoundRect(
                view.x,
                view.y,
                view.x + view.width,
                view.y + view.height,
                radius,
                radius,
                paint
            )
        }
    }

    fun addUserInterest(userInterest: UserInterest) {
        views.add(userInterest)
        invalidate() // 새로운 뷰가 추가되었으니 화면을 다시 그립니다.
    }

    fun updateViewPositions(x: Float, y: Float) {
        views.forEach { userInterest ->
            // 기존 로직
            val deltaX = x * userInterest.moveSensitivity
            val deltaY = y * userInterest.moveSensitivity

            userInterest.x += deltaX
            userInterest.y += deltaY

            // 화면 경계 내에 뷰를 유지
            userInterest.x =
                max(0f, min(userInterest.x, context.getScreenWidth() - userInterest.width))
            // userInterest.height를 더해 하단 경계 계산
            userInterest.y =
                max(0f, min(userInterest.y, context.getScreenHeight() - userInterest.height))
        }

        invalidate()
    }

// Add collision detection and other necessary methods here
}
