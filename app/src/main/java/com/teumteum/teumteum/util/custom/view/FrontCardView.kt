package com.teumteum.teumteum.util.custom.view

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.teumteum.teumteum.R
import com.teumteum.teumteum.util.custom.view.model.FrontCard
import com.teumteum.teumteum.util.extension.dpToPx

/**
 * 카드 전면 뷰
 *
 * xml, compose 모든 환경에서 뷰를 재활용 할 수 있게 커스텀뷰로 제작
 */
class FrontCardView : CardView {
    private val matchParent = ConstraintLayout.LayoutParams.PARENT_ID
    private var frontCard = FrontCard()

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    /**
     * 컴포즈 환경에서 FrontCard 객체 생성
     */
    fun getInstance(frontCard: FrontCard) {
        this.frontCard = frontCard
        setUpViews()
    }


    /**
     * 뷰가 생성되어 layout에 추가된 후 xml의 값이 뷰에 적용
     */
    private fun init(context: Context, attrs: AttributeSet?) {
        setFrontCardBackground(context)
        addView(createLayoutAndViews(context))
        applyXmlAttributes(context, attrs)
    }

    /**
     * 카드 배경 설정
     */
    private fun setFrontCardBackground(context: Context) {
        setCardBackgroundColor(
            ColorStateList.valueOf(
                ContextCompat.getColor(
                    context,
                    R.color.grey_900
                )
            )
        )
        radius = 12.dpToPx(context).toFloat()
        layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
    }

    /**
     * 커스텀뷰의 속성 값을 xml에 입력한 값으로 초기화
     */
    private fun applyXmlAttributes(context: Context, attrs: AttributeSet?) {
        context.theme.obtainStyledAttributes(attrs, R.styleable.CardFrontView, 0, 0).apply {
            try {
                frontCard.name = getString(R.styleable.CardFrontView_name) ?: ""
                frontCard.company = getString(R.styleable.CardFrontView_company) ?: ""
                frontCard.job = getString(R.styleable.CardFrontView_job) ?: ""
                frontCard.level = getString(R.styleable.CardFrontView_level) ?: ""
                frontCard.area = getString(R.styleable.CardFrontView_area) ?: ""
                frontCard.mbti = getString(R.styleable.CardFrontView_mbti) ?: ""
                frontCard.characterResId = getResourceId(R.styleable.CardFrontView_image, 0)
            } finally {
                recycle()
            }
        }
        setUpViews()
    }

    private fun setUpViews() {
        setTextView(tvName, frontCard.name)
        setTextView(tvCompany, frontCard.company)
        setTextView(tvJob, frontCard.job)
        setTextView(tvLevel, frontCard.level)
        setTextView(tvArea, frontCard.area)
        setTextView(tvMbti, frontCard.mbti)
        setImageViewResource(tvCharacter, frontCard.characterResId)
    }

    private fun setTextView(viewId: Int, text: String) {
        val textView = findViewById<TextView>(viewId)
        textView?.text = text
    }

    private fun setImageViewResource(viewId: Int, resId: Int) {
        val imageView = findViewById<ImageView>(viewId)
        imageView?.setImageResource(resId)
    }

    /**
     * layout과 그 안에 포함시킬 뷰 추가
     */
    private fun createLayoutAndViews(context: Context) = ConstraintLayout(context).apply {
        id = clCardFront
        layoutParams =
            ConstraintLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)

        addTextView(
            context,
            id = tvName,
            text = context.getString(R.string.front_card_name),
            textColor = R.color.grey_50,
            textSizeSp = 30f,
            fontFamily = R.font.pretendard_bold,
            lineHeightDp = 36,
            topToTopOf = matchParent,
            startToStartOf = matchParent,
            marginTop = 40,
            marginStart = 32
        )
        addTextView(
            context,
            id = tvCompany,
            text = context.getString(R.string.front_card_company),
            textColor = R.color.grey_400,
            textSizeSp = 16f,
            fontFamily = R.font.pretendard_semibold,
            lineHeightDp = 22,
            startToStartOf = tvName,
            marginTop = 20,
            topToBottomOf = tvName
        )
        addTextView(
            context,
            id = tvJob,
            text = context.getString(R.string.front_card_job),
            textColor = R.color.grey_50,
            textSizeSp = 18f,
            fontFamily = R.font.pretendard_bold,
            lineHeightDp = 24,
            startToStartOf = tvName,
            marginTop = 6,
            topToBottomOf = tvCompany
        )
        addTextView(
            context,
            id = tvLevel,
            text = context.getString(R.string.front_card_level),
            textColor = R.color.grey_300,
            textSizeSp = 12f,
            fontFamily = R.font.pretendard_regular,
            lineHeightDp = 18,
            marginTop = 6,
            paddingStart = 8,
            paddingEnd = 8,
            paddingTop = 2,
            paddingBottom = 2,
            startToStartOf = tvName,
            topToBottomOf = tvJob,
            background = R.drawable.shape_rect4_grey_800
        )
        addTextView(
            context,
            id = tvArea,
            text = context.getString(R.string.front_card_area),
            textColor = R.color.grey_400,
            textSizeSp = 12f,
            fontFamily = R.font.pretendard_regular,
            lineHeightDp = 18,
            marginTop = 6,
            startToStartOf = tvName,
            bottomToTopOf = tvMbti
        )
        addTextView(
            context,
            id = tvMbti,
            text = context.getString(R.string.front_card_mbti),
            textColor = R.color.grey_50,
            textSizeSp = 20f,
            fontFamily = R.font.pretendard_bold,
            lineHeightDp = 28,
            marginBottom = 32,
            startToStartOf = tvName,
            bottomToBottomOf = matchParent
        )
        addImageView(
            context,
            id = tvCharacter,
            drawableRes = R.drawable.ic_card_penguin
        )
    }

    private fun ConstraintLayout.addTextView(
        context: Context,
        id: Int,
        text: String? = "",
        textColor: Int = R.color.grey_50,
        textSizeSp: Float,
        fontFamily: Int,
        lineHeightDp: Int,
        marginTop: Int = 0,
        marginBottom: Int = 0,
        marginStart: Int = 0,
        marginEnd: Int = 0,
        paddingStart: Int = 0,
        paddingEnd: Int = 0,
        paddingTop: Int = 0,
        paddingBottom: Int = 0,
        topToTopOf: Int? = null,
        topToBottomOf: Int? = null,
        bottomToTopOf: Int? = null,
        bottomToBottomOf: Int? = null,
        startToStartOf: Int? = null,
        startToEndOf: Int? = null,
        endToEndOf: Int? = null,
        endToStartOf: Int? = null,
        background: Int? = null
    ) {
        val textView = TextView(context).apply {
            this.id = id
            layoutParams = ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_CONSTRAINT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                topToTopOf?.let { topToTop = it }
                topToBottomOf?.let { topToBottom = it }
                bottomToTopOf?.let { bottomToTop = it }
                bottomToBottomOf?.let { bottomToBottom = it }
                startToStartOf?.let { startToStart = it }
                startToEndOf?.let { startToEnd = it }
                endToEndOf?.let { endToEnd = it }
                endToStartOf?.let { endToStart = it }

                this.topMargin = marginTop.dpToPx(context)
                this.bottomMargin = marginBottom.dpToPx(context)
                this.marginStart = marginStart.dpToPx(context)
                this.marginEnd = marginEnd.dpToPx(context)
            }
            this.text = text
            setTextColor(ContextCompat.getColor(context, textColor))
            setTextSize(TypedValue.COMPLEX_UNIT_SP, textSizeSp)
            this.lineHeight = lineHeightDp.dpToPx(context)
            letterSpacing = 0f
            typeface = ResourcesCompat.getFont(context, fontFamily)
            setPadding(
                paddingStart.dpToPx(context),
                paddingTop.dpToPx(context),
                paddingEnd.dpToPx(context),
                paddingBottom.dpToPx(context)
            )
            background?.let { setBackgroundResource(it) }
        }
        addView(textView)
    }

    private fun ConstraintLayout.addImageView(context: Context, id: Int, drawableRes: Int) {
        val imageView = ImageView(context).apply {
            this.id = id
            layoutParams =
                ConstraintLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
                    .apply {
                        bottomToBottom = matchParent
                        endToEnd = matchParent
                    }
            setImageResource(drawableRes)
        }
        addView(imageView)
    }

    companion object {
        const val clCardFront = 0
        const val tvName = 1
        const val tvCompany = 2
        const val tvJob = 3
        const val tvLevel = 4
        const val tvArea = 5
        const val tvMbti = 6
        const val tvCharacter = 7
    }
}