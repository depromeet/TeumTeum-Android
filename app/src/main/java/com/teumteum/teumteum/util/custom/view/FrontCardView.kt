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

    val tvName: TextView by lazy { findViewById(R.id.tvName) }
    val tvCompany: TextView by lazy { findViewById(R.id.tvCompany) }
    val tvJob: TextView by lazy { findViewById(R.id.tvJob) }
    val tvLevel: TextView by lazy { findViewById(R.id.tvLevel) }
    val tvArea: TextView by lazy { findViewById(R.id.tvArea) }
    val tvMbti: TextView by lazy { findViewById(R.id.tvMbti) }
    val ivCharacter: ImageView by lazy { findViewById(R.id.ivCharacter) }
    val ivFloat: ImageView by lazy { findViewById(R.id.ivFloat) }

    val ivEditName: ImageView by lazy { findViewById(R.id.ivEditName) }
    val ivEditCompany: ImageView by lazy { findViewById(R.id.ivEditCompany) }
    val ivEditJob: ImageView by lazy { findViewById(R.id.ivEditJob) }
    val ivEditArea: ImageView by lazy { findViewById(R.id.ivEditArea) }

    var isModify: Boolean = false
        set(value) {
            field = value
            ivFloat.visibility = if (value) View.VISIBLE else View.INVISIBLE
        }

    var isModifyDetail: Boolean = false
        set(value) {
            field = value
            ivEditName.visibility = if (value) View.VISIBLE else View.INVISIBLE
            ivEditCompany.visibility = if (value) View.VISIBLE else View.INVISIBLE
            ivEditJob.visibility = if (value) View.VISIBLE else View.INVISIBLE
            ivEditArea.visibility = if (value) View.VISIBLE else View.INVISIBLE
        }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    override fun setOnClickListener(listener: OnClickListener?) {
        super.setOnClickListener(listener)
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
                    characterResId =
                        getResourceId(com.teumteum.base.R.styleable.CardFrontView_characterImage, 0)
                    isModify =
                        getBoolean(com.teumteum.base.R.styleable.CardFrontView_isModify, false)
                    isModifyDetail = getBoolean(
                        com.teumteum.base.R.styleable.CardFrontView_isModifyDetail,
                        false
                    )
                }
            } finally {
                recycle()
            }
        }
        setUpViews()
    }

    private fun setUpViews() {
        tvName.text = frontCard.name
        tvCompany.text = frontCard.company
        tvJob.text = frontCard.job
        tvLevel.text = frontCard.level
        tvArea.text = frontCard.area
        tvMbti.text = frontCard.mbti
        ivCharacter.setImageResource(frontCard.characterResId)

        ivFloat.setImageResource(frontCard.floatResId)
        ivEditName.setImageResource(frontCard.editNameResId)
        ivEditCompany.setImageResource(frontCard.editCompanyResId)
        ivEditJob.setImageResource(frontCard.editJobResId)
        ivEditArea.setImageResource(frontCard.editAreaResId)
    }

    /**
     * layout과 그 안에 포함시킬 뷰 추가
     */
    private fun createLayoutAndViews(context: Context) = ConstraintLayout(context).apply {
        id = R.id.cl
        layoutParams =
            ConstraintLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)

        addTextView(
            context,
            id = R.id.tvName,
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
            id = R.id.tvCompany,
            text = context.getString(R.string.front_card_company),
            textColor = com.teumteum.base.R.color.text_body_teritary,
            textSizeSp = 16f,
            fontFamily = com.teumteum.base.R.font.pretendard_semibold,
            lineHeightDp = 22,
            startToStartOf = R.id.tvName,
            marginTop = 20,
            topToBottomOf = R.id.tvName
        )
        addTextView(
            context,
            id = R.id.tvJob,
            text = context.getString(R.string.front_card_job),
            textColor = com.teumteum.base.R.color.text_headline_primary,
            textSizeSp = 18f,
            fontFamily = com.teumteum.base.R.font.pretendard_bold,
            lineHeightDp = 24,
            startToStartOf = R.id.tvName,
            marginTop = 6,
            topToBottomOf = R.id.tvCompany
        )
        addTextView(
            context,
            id = R.id.tvLevel,
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
            startToStartOf = R.id.tvName,
            topToBottomOf = R.id.tvJob,
            background = R.drawable.shape_rect4_elevation_level02
        )
        addTextView(
            context,
            id = R.id.tvArea,
            text = context.getString(R.string.front_card_area),
            textColor = com.teumteum.base.R.color.text_body_teritary,
            textSizeSp = 12f,
            fontFamily = com.teumteum.base.R.font.pretendard_regular,
            lineHeightDp = 18,
            marginTop = 6,
            startToStartOf = R.id.tvName,
            bottomToTopOf = R.id.tvMbti,
            elevationDp = 1f,
        )
        addTextView(
            context,
            id = R.id.tvMbti,
            text = context.getString(R.string.front_card_mbti),
            textColor = com.teumteum.base.R.color.text_headline_primary,
            textSizeSp = 20f,
            fontFamily = com.teumteum.base.R.font.pretendard_bold,
            lineHeightDp = 28,
            marginBottom = 32,
            startToStartOf = R.id.tvName,
            bottomToBottomOf = layoutParent
        )
        addImageView(
            context,
            id = R.id.ivCharacter,
            drawableRes = R.drawable.ic_card_front_penguin,
            bottomToBottomOf = layoutParent,
            endToEndOf = layoutParent,
            elevationDp = 0f,
        )
        addImageView(
            context,
            id = R.id.ivFloat,
            drawableRes = R.drawable.ic_card_float,
            bottomToBottomOf = layoutParent,
            endToEndOf = layoutParent,
            marginBottom = 32,
            marginEnd = 24
        )
        addImageView( //todo - 하나의 id값으로 일괄 관리 가능 여부 확인
            context,
            id = R.id.ivEditName,
            drawableRes = R.drawable.ic_card_edit,
            startToEndOf = R.id.tvName,
            topToTopOf = R.id.tvName,
            bottomToBottomOf = R.id.tvName,
            marginStart = 4
        )
        addImageView(
            context,
            id = R.id.ivEditCompany,
            drawableRes = R.drawable.ic_card_edit,
            startToEndOf = R.id.tvCompany,
            topToTopOf = R.id.tvCompany,
            bottomToBottomOf = R.id.tvCompany,
            marginStart = 4
        )
        addImageView(
            context,
            id = R.id.ivEditJob,
            drawableRes = R.drawable.ic_card_edit,
            startToEndOf = R.id.tvJob,
            topToTopOf = R.id.tvJob,
            bottomToBottomOf = R.id.tvJob,
            marginStart = 4
        )
        addImageView(
            context,
            id = R.id.ivEditArea,
            drawableRes = R.drawable.ic_card_edit,
            startToEndOf = R.id.tvArea,
            topToTopOf = R.id.tvArea,
            bottomToBottomOf = R.id.tvArea,
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
        background: Int? = null,
        elevationDp: Float = 0f,
    ) {
        val textView = TextView(context).apply {
            this.id = id
            this.elevation = elevationDp.dpToPx(context).toFloat()
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
        endToStartOf: Int? = null,
        elevationDp: Float = 0f,
    ) {
        val imageView = ImageView(context).apply {
            this.id = id
            this.elevation = elevationDp.dpToPx(context).toFloat()

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
}