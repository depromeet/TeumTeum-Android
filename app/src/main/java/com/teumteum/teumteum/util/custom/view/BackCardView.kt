package com.teumteum.teumteum.util.custom.view

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.teumteum.teumteum.R
import com.teumteum.teumteum.util.custom.itemdecoration.FlexboxItemDecoration
import com.teumteum.teumteum.util.custom.view.adapter.InterestAdapter
import com.teumteum.teumteum.util.custom.view.model.BackCard
import com.teumteum.teumteum.util.custom.view.model.Interest
import com.teumteum.teumteum.util.extension.dpToPx

/**
 * 카드 후면 뷰
 *
 * xml, compose 모든 환경에서 뷰를 재활용 할 수 있게 커스텀뷰로 제작
 */
class BackCardView : CardView {
    private val layoutParent = ConstraintLayout.LayoutParams.PARENT_ID
    private var backCard = BackCard()

    val tvGoalTitle: TextView by lazy { findViewById(R.id.tvGoalTitle) }
    val tvGoalContent: TextView by lazy { findViewById(R.id.tvGoalContent) }

    val ivCharacter: ImageView by lazy { findViewById(R.id.ivCharacter) }
    val ivFloat: ImageView by lazy { findViewById(R.id.ivFloat) }

    val ivEditGoalContent: ImageView by lazy { findViewById(R.id.ivEditGoalContent) }

    var isModify: Boolean = false
        set(value) {
            field = value
            ivFloat.visibility = if (value) View.VISIBLE else View.INVISIBLE
        }

    var isModifyDetail: Boolean = false
        set(value) {
            field = value
            ivEditGoalContent.visibility = if (value) View.VISIBLE else View.INVISIBLE
        }

    // isModifyDetail 값을 설정하고 어댑터에 UI 갱신을 알리는 함수
    @SuppressLint("NotifyDataSetChanged")
    fun setIsModifyDetail(isModifyDetail: Boolean) {
        this.isModifyDetail = isModifyDetail //todo - 하나의 변수를 어댑터 안팎으로 2개씩 나눠 다루고 있는데 추후 하나로 통일
        interestAdapter.isModifyDetail = isModifyDetail
        interestAdapter.notifyDataSetChanged()
    }

    // 공개 속성으로 RecyclerView와 Adapter 제공
    val interestAdapter = InterestAdapter(context)
    lateinit var rvInterests: RecyclerView
        private set

    fun submitInterestList(interests: List<Interest>) {
        val currentList = interestAdapter.currentList.toMutableList()

        if (!currentList.any { it.interest == "추가하기" }) {
            currentList.add(Interest("추가하기"))
        }

        val availableSlots = 4 - currentList.size

        if (interests.size > availableSlots) {
            Toast.makeText(context, "최대 3개까지 선택할 수 있어요", Toast.LENGTH_SHORT).show()
        } else {
            val interestsToAdd = interests.take(availableSlots)
            currentList.addAll(0, interestsToAdd)

            interestAdapter.submitList(currentList.reversed())
        }
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
    fun getInstance(backCard: BackCard) {
        this.backCard = backCard
        setUpViews()
    }


    /**
     * 뷰가 생성되어 layout에 추가된 후 xml의 값이 뷰에 적용
     */
    private fun init(context: Context, attrs: AttributeSet?) {
        setBackCardBackground(context)
        addView(createLayoutAndViews(context))
        applyXmlAttributes(context, attrs)
    }

    /**
     * 카드 배경 설정
     */
    private fun setBackCardBackground(context: Context) {
        elevation = 0F
        background = ContextCompat.getDrawable(
            context,
            R.drawable.shape_rect12_elevation_level01_outline_level03
        )
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
    }

    /**
     * 커스텀뷰의 속성 값을 xml에 입력한 값으로 초기화
     */
    private fun applyXmlAttributes(context: Context, attrs: AttributeSet?) {
        context.theme.obtainStyledAttributes(
            attrs,
            com.teumteum.base.R.styleable.CardBackView,
            0,
            0
        ).apply {
            try {
                with(backCard) {
                    goalTitle =
                        getString(com.teumteum.base.R.styleable.CardBackView_goalTitle) ?: "GOAL"
                    goalContent =
                        getString(com.teumteum.base.R.styleable.CardBackView_goalContent) ?: ""
                    characterResId =
                        getResourceId(com.teumteum.base.R.styleable.CardBackView_characterImage, 0)
                    isModify =
                        getBoolean(com.teumteum.base.R.styleable.CardBackView_isModify, false)
                }
            } finally {
                recycle()
            }
        }
        setUpViews()
    }

    private fun setUpViews() {
        tvGoalTitle.text = backCard.goalTitle
        tvGoalContent.text = backCard.goalContent
        backCard.characterResId?.let { ivCharacter.setImageResource(it) }
        ivFloat.setImageResource(backCard.floatResId)
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
            id = R.id.tvGoalTitle,
            text = context.getString(R.string.back_card_goal_title),
            textColor = com.teumteum.base.R.color.graphic_skyblue,
            textSizeSp = 14f,
            fontFamily = com.teumteum.base.R.font.pretendard_bold,
            lineHeightDp = 24,
            topToTopOf = layoutParent,
            startToStartOf = layoutParent,
            marginTop = 32,
            marginStart = 32
        )
        addTextView(
            context,
            id = R.id.tvGoalContent,
            text = "가고싶은 회사에 취업해 프로덕트 디자이너로 일하고 싶어요 안녕하세요 저는 사십구자에요", //동적 할당 예정
            textColor = com.teumteum.base.R.color.text_headline_primary,
            textSizeSp = 18f,
            fontFamily = com.teumteum.base.R.font.pretendard_semibold,
            lineHeightDp = 24,
            startToStartOf = R.id.tvGoalTitle,
            endToEndOf = layoutParent,
            marginTop = 16,
            marginEnd = 32,
            topToBottomOf = R.id.tvGoalTitle
        )
        addImageView(
            context,
            id = R.id.ivCharacter,
            bottomToBottomOf = layoutParent,
            endToEndOf = layoutParent
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
            id = R.id.ivEditGoalContent,
            drawableRes = R.drawable.ic_card_edit,
            startToStartOf = R.id.tvGoalContent,
            topToBottomOf = R.id.tvGoalContent,
            marginTop = 4
        )
        addRecyclerView(
            context,
            id = R.id.rvInterest,
            bottomToBottomOf = layoutParent,
            startToStartOf = layoutParent,
            marginBottom = 32,
            marginStart = 32,
            marginEnd = 32,
            itemLeftSpaceDp = 8,
            itemTopSpaceDp = 8,
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
    ) {
        val textView = TextView(context).apply {
            this.id = id
            layoutParams = ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_CONSTRAINT, //MATCH_CONTSRAINT로 설정해야 startToStartOf 등 걸어놓은 constraint가 반영됨
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
        context: Context, id: Int,
        drawableRes: Int? = null,
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
            drawableRes?.let { setImageResource(it) }
        }
        addView(imageView)
    }

    private fun ConstraintLayout.addRecyclerView(
        context: Context,
        id: Int = R.id.rvInterest,
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
        itemLeftSpaceDp: Int,
        itemTopSpaceDp: Int,
    ) {
        rvInterests = RecyclerView(context).apply {
            this.id = id
            layoutManager = FlexboxLayoutManager(context).apply {
                // 항목을 수평 방향으로 배치
                flexDirection = FlexDirection.ROW_REVERSE
                // 항목이 화면을 넘어갈 경우 다음 줄로 넘어가도록 설정
                flexWrap = FlexWrap.WRAP
                // 항목들 사이의 정렬 방식 설정 (옵션)
                justifyContent = JustifyContent.FLEX_END //우측부터 쌓으려면 FLEX_START
            }
            adapter = interestAdapter // Use the existing adapter

            addItemDecoration(
                FlexboxItemDecoration(
                    context,
                    itemLeftSpaceDp,
                    itemTopSpaceDp
                )
            )
            background?.let { setBackgroundResource(it) }
        }
        setPadding(
            paddingStart.dpToPx(context),
            paddingTop.dpToPx(context),
            paddingEnd.dpToPx(context),
            paddingBottom.dpToPx(context)
        )
        val layoutParams = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.MATCH_PARENT,
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
        rvInterests.layoutParams = layoutParams
        this.addView(rvInterests)
    }
}