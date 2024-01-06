package com.teumteum.teumteum.presentation.teumteum.shake.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.teumteum.base.util.TransformUtils
import com.teumteum.teumteum.R
import com.teumteum.teumteum.presentation.teumteum.shake.model.UserInterest
import com.teumteum.teumteum.util.extension.getScreenHeight
import com.teumteum.teumteum.util.extension.getScreenWidth
import timber.log.Timber
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
        views.forEach { view ->
            // 도형 그리기
            paint.color = view.color
            val radius = TransformUtils.dpToPx(4f).toFloat()
            canvas.drawRoundRect(view.x, view.y, view.x + view.width, view.y + view.height, radius, radius, paint)

            // 텍스트 그리기
            paint.color = Color.BLACK // 텍스트 색상
            paint.textSize = TransformUtils.dpToPx(16f).toFloat() // 텍스트 크기
            paint.textAlign = Paint.Align.CENTER // 텍스트 정렬
            val textX = view.x + view.width / 2
            val textY = view.y + view.height / 2 - (paint.descent() + paint.ascent()) / 2
            canvas.drawText(view.text, textX, textY, paint)
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
