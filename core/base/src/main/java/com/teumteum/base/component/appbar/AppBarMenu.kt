package com.teumteum.base.component.appbar

import android.graphics.Typeface
import com.teumteum.base.component.ripple.RippleEffect

sealed class AppBarMenu(
    open val resourceId: Int,
    open val paddingDip: Int,
    open val useRippleEffect: Boolean,
    open val rippleEffect: RippleEffect,
    open val clickEvent: (() -> Unit)?
) {

    data class IconStyle(
        override val resourceId: Int = AppBarLayout.MENU_ITEM_RESOURCE_NONE,
        override val paddingDip: Int = AppBarLayout.MENU_ITEM_PADDING,
        override val clickEvent: (() -> Unit)? = null,
        override val useRippleEffect: Boolean = false,
        override val rippleEffect: RippleEffect = RippleEffect.CircleRipple,
        val widthDip: Int = AppBarLayout.MENU_ITEM_SIZE,
        val heightDip: Int = AppBarLayout.MENU_ITEM_SIZE,

        ) : AppBarMenu(resourceId, paddingDip, useRippleEffect, rippleEffect, clickEvent)

    data class TextStyle(
        override val resourceId: Int = AppBarLayout.MENU_ITEM_RESOURCE_NONE,
        override val paddingDip: Int = AppBarLayout.MENU_ITEM_PADDING,
        override val useRippleEffect: Boolean = false,
        override val rippleEffect: RippleEffect = RippleEffect.CircleRipple,
        override val clickEvent: (() -> Unit)? = null,
        val textSizeDip: Int = AppBarLayout.MENU_ITEM_TEXT_SIZE,
        val textColorResId: Int = AppBarLayout.MENU_ITEM_TEXT_COLOR,
        val textTypeface: Typeface = Typeface.DEFAULT_BOLD
    ) : AppBarMenu(resourceId, paddingDip, useRippleEffect, rippleEffect, clickEvent)
}