package com.teumteum.teumteum.util.custom.view

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
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
    private val layoutParent = ConstraintLayout.LayoutParams.PARENT_ID
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
        elevation = 0F
        background = ContextCompat.getDrawable(
            context,
            R.drawable.shape_rect12_elevation_level01_outline_level03
        )
        layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
    }

    /**
     * 커스텀뷰의 속성 값을 xml에 입력한 값으로 초기화
     */
    private fun applyXmlAttributes(context: Context, attrs: AttributeSet?) {
        context.theme.obtainStyledAttributes(
            attrs,
            com.teumteum.base.R.styleable.CardFrontView,
            0,
            0
        ).apply {
            try {
                with(frontCard) {
                    name = getString(com.teumteum.base.R.styleable.CardFrontView_name) ?: ""
                    company = getString(com.teumteum.base.R.styleable.CardFrontView_company) ?: ""
                    job = getString(com.teumteum.base.R.styleable.CardFrontView_job) ?: ""
                    level = getString(com.teumteum.base.R.styleable.CardFrontView_level) ?: ""
                    area = getString(com.teumteum.base.R.styleable.CardFrontView_area) ?: ""
                    mbti = getString(com.teumteum.base.R.styleable.CardFrontView_mbti) ?: ""
                    characterResId = getResourceId(com.teumteum.base.R.styleable.CardFrontView_characterImage, 0)
                    isModify = getBoolean(com.teumteum.base.R.styleable.CardFrontView_isModify, false)
                    isModifyDetail = getBoolean(com.teumteum.base.R.styleable.CardFrontView_isModifyDetail, false)
                }
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

        val ivFloatVisibility = if (frontCard.isModify == true) View.VISIBLE else View.INVISIBLE
        val ivEditVisibility = if (frontCard.isModifyDetail == true) View.VISIBLE else View.INVISIBLE

        // 이미지 뷰 프로퍼티 설정을 위한 공통 함수 호출
        setImageViewProperties(ivFloat, ivFloatVisibility, frontCard.floatResId)
        setImageViewProperties(ivEditName, ivEditVisibility, frontCard.editNameResId)
        setImageViewProperties(ivEditCompany, ivEditVisibility, frontCard.editCompanyResId)
        setImageViewProperties(ivEditJob, ivEditVisibility, frontCard.editJobResId)
        setImageViewProperties(ivEditArea, ivEditVisibility, frontCard.editAreaResId)
    }

    private fun setImageViewProperties(viewId: Int, visibility: Int, resId: Int?) {
        val imageView = findViewById<ImageView>(viewId)
        imageView?.let {
            it.visibility = visibility
            resId?.let { imageResId -> it.setImageResource(imageResId) }
        }
    }

    private fun setTextView(viewId: Int, text: String) {
        val textView = findViewById<TextView>(viewId)
        textView?.text = text
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
            textColor = com.teumteum.base.R.color.text_headline_primary,
            textSizeSp = 30f,
            fontFamily = com.teumteum.base.R.font.pretendard_bold,
            lineHeightDp = 36,
            topToTopOf = layoutParent,
            startToStartOf = layoutParent,
            marginTop = 40,
            marginStart = 32
        )
        addTextView(
            context,
            id = tvCompany,
            text = context.getString(R.string.front_card_company),
            textColor = com.teumteum.base.R.color.text_body_teritary,
            textSizeSp = 16f,
            fontFamily = com.teumteum.base.R.font.pretendard_semibold,
            lineHeightDp = 22,
            startToStartOf = tvName,
            marginTop = 20,
            topToBottomOf = tvName
        )
        addTextView(
            context,
            id = tvJob,
            text = context.getString(R.string.front_card_job),
            textColor = com.teumteum.base.R.color.text_headline_primary,
            textSizeSp = 18f,
            fontFamily = com.teumteum.base.R.font.pretendard_bold,
            lineHeightDp = 24,
            startToStartOf = tvName,
            marginTop = 6,
            topToBottomOf = tvCompany
        )
        addTextView(
            context,
            id = tvLevel,
            text = context.getString(R.string.front_card_level),
            textColor = com.teumteum.base.R.color.text_body_secondary,
            textSizeSp = 12f,
            fontFamily = com.teumteum.base.R.font.pretendard_regular,
            lineHeightDp = 18,
            marginTop = 6,
            paddingStart = 8,
            paddingEnd = 8,
            paddingTop = 2,
            paddingBottom = 2,
            startToStartOf = tvName,
            topToBottomOf = tvJob,
            background = R.drawable.shape_rect4_elevation_level02
        )
        addTextView(
            context,
            id = tvArea,
            text = context.getString(R.string.front_card_area),
            textColor = com.teumteum.base.R.color.text_body_teritary,
            textSizeSp = 12f,
            fontFamily = com.teumteum.base.R.font.pretendard_regular,
            lineHeightDp = 18,
            marginTop = 6,
            startToStartOf = tvName,
            bottomToTopOf = tvMbti
        )
        addTextView(
            context,
            id = tvMbti,
            text = context.getString(R.string.front_card_mbti),
            textColor = com.teumteum.base.R.color.text_headline_primary,
            textSizeSp = 20f,
            fontFamily = com.teumteum.base.R.font.pretendard_bold,
            lineHeightDp = 28,
            marginBottom = 32,
            startToStartOf = tvName,
            bottomToBottomOf = layoutParent
        )
        addImageView(
            context,
            id = ivCharacter,
            drawableRes = R.drawable.ic_card_penguin,
            bottomToBottomOf = layoutParent,
            endToEndOf = layoutParent
        )
        addImageView(
            context,
            id = ivFloat,
            drawableRes = R.drawable.ic_card_float,
            bottomToBottomOf = layoutParent,
            endToEndOf = layoutParent,
            marginBottom = 32,
            marginEnd = 24
        )
        addImageView( //todo - 하나의 id값으로 일괄 관리 가능 여부 확인
            context,
            id = ivEditName,
            drawableRes = R.drawable.ic_card_edit,
            startToEndOf = tvName,
            topToTopOf = tvName,
            bottomToBottomOf = tvName,
            marginStart = 4
        )
        addImageView(
            context,
            id = ivEditCompany,
            drawableRes = R.drawable.ic_card_edit,
            startToEndOf = tvCompany,
            topToTopOf = tvCompany,
            bottomToBottomOf = tvCompany,
            marginStart = 4
        )
        addImageView(
            context,
            id = ivEditJob,
            drawableRes = R.drawable.ic_card_edit,
            startToEndOf = tvJob,
            topToTopOf = tvJob,
            bottomToBottomOf = tvJob,
            marginStart = 4
        )
        addImageView(
            context,
            id = ivEditArea,
            drawableRes = R.drawable.ic_card_edit,
            startToEndOf = tvArea,
            topToTopOf = tvArea,
            bottomToBottomOf = tvArea,
            marginStart = 4
        )
    }

    private fun ConstraintLayout.addTextView(
        context: Context,
        id: Int,
        text: String? = "",
        textColor: Int = com.teumteum.base.R.color.grey_50,
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

    private fun ConstraintLayout.addImageView(
        context: Context, id: Int, drawableRes: Int,
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
        endToStartOf: Int? = null
    ) {
        val imageView = ImageView(context).apply {
            this.id = id
            layoutParams =
                ConstraintLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
                    .apply {
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
            setPadding(
                paddingStart.dpToPx(context),
                paddingTop.dpToPx(context),
                paddingEnd.dpToPx(context),
                paddingBottom.dpToPx(context)
            )
            setImageResource(drawableRes)
        }
        addView(imageView)
    }

    /**
     * 추가한 View들에 대한 터치 리스너
     */
    fun setClickListener(viewId: Int, listener: View.OnClickListener) {
        findViewById<View>(viewId)?.setOnClickListener(listener)
    }

    companion object {
        const val clCardFront = 0
        const val tvName = 1
        const val tvCompany = 2
        const val tvJob = 3
        const val tvLevel = 4
        const val tvArea = 5
        const val tvMbti = 6
        const val ivCharacter = 7
        const val ivFloat = 8
        const val ivEditName = 9
        const val ivEditCompany = 10
        const val ivEditJob = 11
        const val ivEditArea = 12
    }
}