package com.teumteum.teumteum.presentation.familiar.shake

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.teumteum.base.util.TransformUtils
import com.teumteum.teumteum.R
import com.teumteum.teumteum.presentation.familiar.shake.model.InterestViewConfig
import java.lang.Float.max
import java.lang.Float.min

class InterestView(
    context: Context,
    attrs: AttributeSet? = null,
) : View(context, attrs) {
    private val paint = Paint()

    private val views = mutableListOf<InterestViewConfig>()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        views.forEach { view ->
            // 도형 그리기
            paint.color = view.color
            val radius = TransformUtils.dpToPx(4f).toFloat()
            canvas.drawRoundRect(view.x, view.y, view.x + view.width, view.y + view.height, radius, radius, paint)
            paint.typeface = ResourcesCompat.getFont(context, com.teumteum.base.R.font.pretendard_semibold)

            // 텍스트 그리기
            paint.color = ContextCompat.getColor(context, com.teumteum.base.R.color.elevation_level01) // 텍스트 색상
            paint.textSize = TransformUtils.dpToPx(16f).toFloat() // 텍스트 크기
            paint.textAlign = Paint.Align.CENTER // 텍스트 정렬
            val textX = view.x + view.width / 2
            val textY = view.y + view.height / 2 - (paint.descent() + paint.ascent()) / 2
            canvas.drawText(view.text, textX, textY, paint)
        }
    }

    fun addUserInterest(interestViewConfig: InterestViewConfig) {
        views.add(interestViewConfig)
        invalidate() // 새로운 뷰가 추가되었으니 화면을 다시 그립니다.
    }

    fun updateViewPositions(x: Float, y: Float) {
        views.forEach { userInterest ->
            // 목표 위치 계산
            var targetX = userInterest.x + x * userInterest.moveSensitivity
            var targetY = userInterest.y + y * userInterest.moveSensitivity

            // 화면 경계를 고려하여 목표 위치 조정
            targetX = max(0f, min(targetX, width.toFloat() - userInterest.width))
            targetY = max(0f, min(targetY, height.toFloat() - userInterest.height))

            // X 좌표 애니메이션
            ValueAnimator.ofFloat(userInterest.x, targetX).apply {
                duration = 100 // 애니메이션 지속 시간
                addUpdateListener { animation ->
                    userInterest.x = animation.animatedValue as Float
                    invalidate()
                }
                start()
            }

            // Y 좌표 애니메이션
            ValueAnimator.ofFloat(userInterest.y, targetY).apply {
                duration = 100 // 애니메이션 지속 시간
                addUpdateListener { animation ->
                    userInterest.y = animation.animatedValue as Float
                    invalidate()
                }
                start()
            }
        }
    }

// Add collision detection and other necessary methods here
}
