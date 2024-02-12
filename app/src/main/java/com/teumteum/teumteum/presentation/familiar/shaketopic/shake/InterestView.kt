package com.teumteum.teumteum.presentation.familiar.shaketopic.shake

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.teumteum.base.util.TransformUtils
import com.teumteum.teumteum.presentation.familiar.shaketopic.shake.model.InterestViewConfig
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
            canvas.drawRoundRect(
                view.x,
                view.y,
                view.x + view.width,
                view.y + view.height,
                radius,
                radius,
                paint
            )

            // 텍스트 그리기
            paint.color =
                ContextCompat.getColor(context, com.teumteum.base.R.color.elevation_level01)
            paint.textSize = TransformUtils.dpToPx(16f).toFloat()
            paint.typeface =
                ResourcesCompat.getFont(context, com.teumteum.base.R.font.pretendard_semibold)
            paint.textAlign = Paint.Align.CENTER
            val textX = view.x + view.width / 2
            val textY = view.y + view.height / 2 - (paint.descent() + paint.ascent()) / 2
            canvas.drawText(view.text, textX, textY, paint)
        }
    }

    fun addUserInterest(interestViewConfig: InterestViewConfig) {
        views.add(interestViewConfig)
        resolveCollisions()
        invalidate()
    }


    fun updateViewPositions(x: Float, y: Float) {
        views.forEach { userInterest ->
            // 목표 위치 계산
            var targetX = userInterest.x + x * userInterest.moveSensitivity
            var targetY = userInterest.y + y * userInterest.moveSensitivity

            // 화면 경계를 고려하여 목표 위치 조정
            targetX = max(0f, min(targetX, width.toFloat() - userInterest.width))
            targetY = max(0f, min(targetY, height.toFloat() - userInterest.height))

            // 위치 애니메이션 시작
            startAnimation(userInterest, targetX, targetY)
        }
        resolveCollisions() // 위치 업데이트 후 충돌 해결
        invalidate() // 위치 업데이트 및 충돌 해결 후 화면 갱신
    }

    private fun startAnimation(userInterest: InterestViewConfig, targetX: Float, targetY: Float) {
        // X 좌표 애니메이션
        ValueAnimator.ofFloat(userInterest.x, targetX).apply {
            duration = 100
            addUpdateListener { animation ->
                userInterest.x = animation.animatedValue as Float
                invalidate()
            }
            start()
        }

        // Y 좌표 애니메이션
        ValueAnimator.ofFloat(userInterest.y, targetY).apply {
            duration = 100
            addUpdateListener { animation ->
                userInterest.y = animation.animatedValue as Float
                invalidate()
            }
            start()
        }
    }

    private fun resolveCollisions() {
        for (i in views.indices) {
            for (j in i + 1 until views.size) {
                val view1 = views[i]
                val view2 = views[j]

                if (isColliding(view1, view2)) {
                    resolveCollision(view1, view2)
                }
            }
        }
    }

    private fun isColliding(view1: InterestViewConfig, view2: InterestViewConfig): Boolean {
        return view1.x < view2.x + view2.width &&
                view1.x + view1.width > view2.x &&
                view1.y < view2.y + view2.height &&
                view1.y + view1.height > view2.y
    }

    private fun resolveCollision(view1: InterestViewConfig, view2: InterestViewConfig) {
        val overlapX =
            calculateOverlap(view1.x, view1.width.toFloat(), view2.x, view2.width.toFloat())
        val overlapY =
            calculateOverlap(view1.y, view1.height.toFloat(), view2.y, view2.height.toFloat())

        // Increase the bounce distance (e.g., 1.5 times the overlap)
        val bounceFactor = 1.5f

        if (overlapX < overlapY) {
            if (view1.x < view2.x) {
                view1.x -= overlapX / 2 * bounceFactor
                view2.x += overlapX / 2 * bounceFactor
            } else {
                view1.x += overlapX / 2 * bounceFactor
                view2.x -= overlapX / 2 * bounceFactor
            }
        } else {
            if (view1.y < view2.y) {
                view1.y -= overlapY / 2 * bounceFactor
                view2.y += overlapY / 2 * bounceFactor
            } else {
                view1.y += overlapY / 2 * bounceFactor
                view2.y -= overlapY / 2 * bounceFactor
            }
        }

        // Ensure views stay within bounds after bouncing
        constrainWithinBounds(view1)
        constrainWithinBounds(view2)
    }

    private fun constrainWithinBounds(view: InterestViewConfig) {
        view.x = max(0f, min(view.x, width.toFloat() - view.width))
        view.y = max(0f, min(view.y, height.toFloat() - view.height))
    }

    private fun calculateOverlap(
        start1: Float,
        length1: Float,
        start2: Float,
        length2: Float
    ): Float {
        return max(0f, min(start1 + length1, start2 + length2) - max(start1, start2))
    }
}
