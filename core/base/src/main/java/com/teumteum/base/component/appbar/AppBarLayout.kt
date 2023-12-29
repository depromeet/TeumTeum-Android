package com.teumteum.base.component.appbar

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.core.view.setPadding
import com.teumteum.base.component.ripple.RippleEffect
import com.teumteum.base.R
import com.teumteum.base.databinding.LayoutCommonAppbarBinding
import com.teumteum.base.util.TransformUtils
import com.teumteum.base.util.extension.gone
import com.teumteum.base.util.extension.visible

interface AppBarLayout {

    companion object {
        // AppBar 기본 높이 값
        private const val APP_BAR_HEIGHT = 44

        // AppBar 기본 백그라운드 색상
        private val APP_BAR_BACKGROUND_COLOR = R.color.c_151515

        // 타이틀 텍스트 기본 크기
        private const val TITLE_TEXT_SIZE = 16

        // 타이틀 텍스트 기본 색상
        private val TITLE_TEXT_COLOR = R.color.c_5f5151

        // 타이틀 아이콘 기본 크기
        private const val TITLE_ICON_SIZE = 24

        // 타이틀 아이콘 없음
        private const val TITLE_ICON_NONE = -1

        // 메뉴 아이템 뷰 기본 크기
        const val MENU_ITEM_SIZE = 40

        // 메뉴 아이템 뷰 기본 패딩
        const val MENU_ITEM_PADDING = 8

        // 메뉴 아이템 리소스(아이콘,텍스트) 없음
        const val MENU_ITEM_RESOURCE_NONE = -1

        // 메뉴 아이템 뷰 텍스트 기본 크기
        const val MENU_ITEM_TEXT_SIZE = 16

        // 메뉴 아이템 뷰 텍스트 기본 색상
        val MENU_ITEM_TEXT_COLOR = R.color.c_5e5050

        // 메뉴 뒤로가기 기본 아이콘
        private const val MENU_ICON_BACK = -1

        // 메뉴 최대 노출 갯수
        private const val MENU_LIMIT_COUNT = 3

        // 뷰 사이즈 없음
        private const val VIEW_SIZE_NONE = -1
    }

    val appBarBinding: LayoutCommonAppbarBinding

    /**
     * AppBar의 초기화 코드 정의
     */
    fun initAppBarLayout()

    /**
     * AppBar의 배경 색상 설정
     */
    fun setAppBarBackgroundColor(@ColorRes colorResId: Int = APP_BAR_BACKGROUND_COLOR) {
        appBarBinding.clAppBar.run {
            if (context != null) {
                setBackgroundColor(ContextCompat.getColor(context, colorResId))
            }
        }
    }

    /**
     * AppBar의 높이 설정
     */
    fun setAppBarHeight(height: Int = APP_BAR_HEIGHT) {
        setViewSize(appBarBinding.clAppBar, height = TransformUtils.dpToPx(dp = height))
    }

    /**
     * AppBar의 Title Text 설정
     */
    fun setAppBarTitleText(@StringRes textResId: Int) {
        appBarBinding.tvTitle.run {
            if (context != null) {
                text = context.getText(textResId)
            }
        }
    }

    /**
     * AppBar의 Title Text 설정
     */
    fun setAppBarTitleText(text: CharSequence?) {
        text?.also {
            appBarBinding.tvTitle.text = it
        }
    }

    /**
     * AppBar의 Title Text Color 설정
     */
    fun setAppBarTitleTextColor(@ColorRes textColorResId: Int = TITLE_TEXT_COLOR) {
        appBarBinding.tvTitle.run {
            if (context != null) {
                setTextColor(ContextCompat.getColor(context, textColorResId))
            }
        }
    }

    /**
     * AppBar의 Title Text Size 설정
     */
    fun setAppBarTitleTextSize(textSizeDip: Int = TITLE_TEXT_SIZE) {
        appBarBinding.tvTitle.run {
            if (context != null) {
                setTextSize(TypedValue.COMPLEX_UNIT_DIP, textSizeDip.toFloat())
            }
        }
    }

    /**
     * AppBar의 Title Icon 설정
     */
    fun setAppBarTitleIcon(@DrawableRes iconResId: Int = TITLE_ICON_NONE) {
        appBarBinding.ivTitle.run {
            if (iconResId != TITLE_ICON_NONE) {
                visible()
                setImageResource(iconResId)

            } else {
                gone()
            }
        }
    }

    /**
     * AppBar의 Title Icon 설정
     */
    fun setAppBarTitleIcon(drawable: Drawable?) {
        drawable?.also {
            appBarBinding.ivTitle.run {
                visible()
                setImageDrawable(it)
            }
        }
    }

    /**
     * AppBar의 Title Icon Size 설정
     */
    fun setAppBarTitleIconSize(iconSizeDip: Int = TITLE_ICON_SIZE) {
        appBarBinding.ivTitle.run {
            setViewSize(
                this,
                TransformUtils.dpToPx(dp = iconSizeDip),
                TransformUtils.dpToPx(dp =iconSizeDip)
            )
        }
    }

    /**
     * AppBar의 Title 영역 클릭 이벤트 추가
     */
    fun addOnAppBarTitleClickEvent(clickEvent: (() -> Unit)?) {
        appBarBinding.llTitle.setOnClickListener {
            clickEvent?.invoke()
        }
    }

    /**
     * 메뉴 영역에 아이템 추가 가능 여부 반환
     */
    private fun canAddMenu(parent: LinearLayout): Boolean = parent.childCount < MENU_LIMIT_COUNT

    /**
     * AppBar의 왼쪽 메뉴 아이템 추가
     */
    fun addMenuToLeft(vararg menu: AppBarMenu) {
        appBarBinding.llLeftMenu.run {
            if (context != null) {
                menu.forEach { item ->
                    if (canAddMenu(this)) {
                        addMenuView(context, this, item)
                    } else {
                        return@forEach
                    }
                }
            }
        }
    }

    /**
     * AppBar의 왼쪽 메뉴 아이템 추가
     */
    fun addMenuToLeft(menuView: View?) {
        if (menuView != null) {
            appBarBinding.llLeftMenu.run {
                if (canAddMenu(this)) {
                    addView(menuView)
                }
            }
        }
    }

    /**
     * AppBar의 오른쪽 메뉴 아이템 추가
     */
    fun addMenuToRight(vararg menu: AppBarMenu) {
        appBarBinding.llRightMenu.run {
            if (context != null) {
                menu.forEach { item ->
                    if (canAddMenu(this)) {
                        addMenuView(context, this, item)
                    } else {
                        return@forEach
                    }
                }
            }
        }
    }

    /**
     * AppBar의 오른쪽 메뉴 추가
     */
    fun addMenuToRight(menuView: View?) {
        if (menuView != null) {
            appBarBinding.llRightMenu.run {
                if (canAddMenu(this)) {
                    addView(menuView)
                }
            }
        }
    }

    /**
     * 해당 index의 메뉴 존재 여부 반환
     */
    private fun isValidMenu(parent: LinearLayout, index: Int): Boolean = index < parent.childCount

    /**
     * AppBar의 왼쪽 메뉴(index) 제거
     */
    fun removeLeftMenu(index: Int) {
        try {
            appBarBinding.llLeftMenu.run {
                if (isValidMenu(this, index)) {
                    removeViewAt(index)
                }
            }
        } catch (e: Exception) {
            Log.e("TEUMTEUM-LOG","${e.message}")
        }
    }

    /**
     * AppBar의 왼쪽 모든 메뉴 제거
     */
    fun removeAllLeftMenu() {
        appBarBinding.llLeftMenu.removeAllViewsInLayout()
    }

    /**
     * AppBar의 오른쪽 메뉴(index) 제거
     */
    fun removeRightMenu(index: Int) {
        try {
            appBarBinding.llRightMenu.run {
                if (isValidMenu(this, index)) {
                    removeViewAt(index)
                }
            }
        } catch (e: Exception) {
            Log.e("TEUMTEUM-LOG","${e.message}")
        }
    }

    /**
     * AppBar의 오른쪽 모든 메뉴 제거
     */
    fun removeAllRightMenu() {
        appBarBinding.llRightMenu.removeAllViewsInLayout()
    }

    /**
     * AppBar 메뉴 추가
     * parent : 메뉴가 추가될 부모 뷰
     * appBarMenu : 추가할 메뉴
     */
    private fun addMenuView(context: Context, parent: LinearLayout, appBarMenu: AppBarMenu) {
        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )

        var menuView: View? = null
        when (appBarMenu) {
            is AppBarMenu.IconStyle -> {
                menuView = ImageView(context).apply {
                    appBarMenu.run {
                        // 이미지뷰 패딩 설정 (기본 8dp)
                        setPadding(TransformUtils.dpToPx(paddingDip))

                        // 이미지 아이콘
                        setImageResource(resourceId)

                        // 리플 효과
                        if (useRippleEffect) {
                            setRippleEffect(context, this@apply, rippleEffect)
                        }

                        // 클릭 이벤트
                        setOnClickListener {
                            appBarMenu.clickEvent?.invoke()
                        }

                        // 이미지뷰 사이즈 (기본 40*40dp)
                        layoutParams.width = TransformUtils.dpToPx(widthDip)
                        layoutParams.height = TransformUtils.dpToPx(heightDip)
                        setLayoutParams(layoutParams)
                    }
                }
            }

            is AppBarMenu.TextStyle -> {
                menuView = TextView(context).apply {
                    appBarMenu.run {
                        // 텍스트뷰 패딩 설정 (기본 8dp)
                        val horizontalPadding = TransformUtils.dpToPx(paddingDip)
                        setPadding(horizontalPadding, 0, horizontalPadding, 0)

                        // 텍스트 스타일
                        text = context.getString(resourceId)
                        gravity = Gravity.CENTER
                        typeface = textTypeface
                        setTextSize(TypedValue.COMPLEX_UNIT_DIP, textSizeDip.toFloat())
                        setTextColor(ContextCompat.getColor(context, textColorResId))

                        // 리플 효과
                        if (useRippleEffect) {
                            setRippleEffect(context, this@apply, rippleEffect)
                        }

                        // 클릭 이벤트
                        setOnClickListener {
                            appBarMenu.clickEvent?.invoke()
                        }

                        // 텍스트뷰 사이즈
                        setLayoutParams(layoutParams)
                    }
                }
            }
        }

        parent.addView(menuView)
    }

    private fun setRippleEffect(context: Context, view: View, rippleEffect: RippleEffect) {
        when (rippleEffect) {
            is RippleEffect.Ripple -> {
                with(TypedValue()) {
                    context.theme.resolveAttribute(
                        android.R.attr.selectableItemBackground,
                        this,
                        true
                    )
                    view.setBackgroundResource(resourceId)
                }
            }

            is RippleEffect.CircleRipple -> {
                with(TypedValue()) {
                    context.theme.resolveAttribute(
                        android.R.attr.selectableItemBackgroundBorderless,
                        this,
                        true
                    )
                    view.setBackgroundResource(resourceId)
                }
            }


            is RippleEffect.CustomRipple -> {
                view.setBackgroundResource(rippleEffect.rippleEffectResId)
            }
        }
    }

    /**
     * AppBar의 왼쪽 메뉴(index) 보이기
     */
    fun showLeftMenu(index: Int) {
        try {
            appBarBinding.llLeftMenu.run {
                if (isValidMenu(this, index)) {
                    getChildAt(index).visible()
                }
                requestLayout()
            }
        } catch (e: Exception) {
            Log.e("TEUMTEUM-LOG","${e.message}")
        }
    }

    /**
     * AppBar의 왼쪽 모든 메뉴 보이기
     */
    fun showAllLeftMenu() {
        appBarBinding.llLeftMenu.visible()
    }

    /**
     * AppBar의 오른쪽 메뉴(index) 보이기
     */
    fun showRightMenu(index: Int) {
        try {
            appBarBinding.llRightMenu.run {
                if (isValidMenu(this, index)) {
                    getChildAt(index).visible()
                }
                requestLayout()
            }
        } catch (e: Exception) {
            Log.e("TEUMTEUM-LOG","${e.message}")
        }
    }

    /**
     * AppBar의 오른쪽 모든 메뉴 보이기
     */
    fun showAllRightMenu() {
        appBarBinding.llRightMenu.visible()
    }

    /**
     * AppBar에 왼쪽 메뉴(index) 숨기기
     */
    fun hideLeftMenu(index: Int, visibility: Int = View.GONE) {
        try {
            appBarBinding.llLeftMenu.run {
                if (isValidMenu(this, index)) {
                    getChildAt(index).visibility = visibility
                }
                requestLayout()
            }
        } catch (e: Exception) {
            Log.e("TEUMTEUM-LOG","${e.message}")
        }
    }

    /**
     * AppBar의 왼쪽 모든 메뉴 숨기기
     */
    fun hideAllLeftMenu() {
        appBarBinding.llLeftMenu.gone()
    }

    /**
     * AppBar의 오른쪽 메뉴(index) 숨기기
     */
    fun hideRightMenu(index: Int, visibility: Int = View.GONE) {
        try {
            appBarBinding.llRightMenu.run {
                if (isValidMenu(this, index)) {
                    getChildAt(index).visibility = visibility
                }
                requestLayout()
            }
        } catch (e: Exception) {
            Log.e("TEUMTEUM-LOG","${e.message}")
        }
    }

    /**
     * AppBar의 오른쪽 모든 메뉴 숨기기
     */
    fun hideAllRightMenu() {
        appBarBinding.llRightMenu.gone()
    }

    /**
     * 뷰 size 설정
     */
    private fun setViewSize(view: View, width: Int = VIEW_SIZE_NONE, height: Int = VIEW_SIZE_NONE) {
        view.run {
            val params = layoutParams

            if (width > VIEW_SIZE_NONE) {
                params.width = width
            }

            if (height > VIEW_SIZE_NONE) {
                params.height = height
            }

            layoutParams = params
        }
    }

    /**
     * AppBar의 오른쪽 메뉴 뷰 반환
     */
    fun getRightMenuChildAt(index: Int) = appBarBinding.llRightMenu.getChildAt(index)

    /**
     * AppBar의 왼쪽 메뉴 뷰 반환
     */
    fun getLeftMenuChildAt(index: Int) = appBarBinding.llLeftMenu.getChildAt(index)
}