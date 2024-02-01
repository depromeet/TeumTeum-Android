package com.teumteum.teumteum.util

import android.content.Context
import android.graphics.Color
import androidx.core.content.ContextCompat
import com.teumteum.teumteum.R

object ResMapper {
    fun getCharacterDrawableById(characterId: Int): Int {
        return when (characterId) {
            0 -> R.drawable.ic_ghost
            1 -> R.drawable.ic_star_character
            2 -> R.drawable.ic_bear
            3 -> R.drawable.ic_raccoon
            4 -> R.drawable.ic_cat
            5 -> R.drawable.ic_rabbit
            6 -> R.drawable.ic_fox
            7 -> R.drawable.ic_water
            8 -> R.drawable.ic_penguin
            9 -> R.drawable.ic_dog
            10 -> R.drawable.ic_mouse
            11 -> R.drawable.ic_panda
            else -> R.drawable.ic_raccoon //기본값
        }
    }

    fun getFrontCardCharacterDrawableById(characterId: Int): Int {
        return when (characterId) {
            0 -> R.drawable.ic_card_front_ghost
            1 -> R.drawable.ic_card_front_star
            2 -> R.drawable.ic_card_front_bear
            3 -> R.drawable.ic_card_front_raccon
            4 -> R.drawable.ic_card_front_cat
            5 -> R.drawable.ic_card_front_rabbit
            6 -> R.drawable.ic_card_front_fox
            7 -> R.drawable.ic_card_front_water
            8 -> R.drawable.ic_card_front_penguin
            9 -> R.drawable.ic_card_front_dog
            10 -> R.drawable.ic_card_front_mouse
            11 -> R.drawable.ic_card_front_panda
            else -> R.drawable.ic_card_front_raccon //기본값
        }
    }

    fun getBackCardCharacterDrawableById(characterId: Int): Int {
        return when (characterId) {
            0 -> R.drawable.ic_card_back_ghost
            1 -> R.drawable.ic_card_back_star
            2 -> R.drawable.ic_card_back_bear
            3 -> R.drawable.ic_card_back_raccon
            4 -> R.drawable.ic_card_back_cat
            5 -> R.drawable.ic_card_back_rabbit
            6 -> R.drawable.ic_card_back_fox
            7 -> R.drawable.ic_card_back_water
            8 -> R.drawable.ic_card_back_penguin
            9 -> R.drawable.ic_card_back_dog
            10 -> R.drawable.ic_card_back_mouse
            11 -> R.drawable.ic_card_back_panda
            else -> R.drawable.ic_card_back_raccon //기본값
        }
    }


    fun getColorByCharacterId(characterId: Int): Int {
        return when (characterId) {
            0 -> Color.parseColor("#5DEBFF") // For ic_card_ghost
            1 -> Color.parseColor("#FFF174") // For ic_card_star
            2 -> Color.parseColor("#D0DAE4") // For ic_card_bear
            3 -> Color.parseColor("#90FFDE") // For ic_card_raccon
            4 -> Color.parseColor("#FFA827") // For ic_card_cat
            5 -> Color.parseColor("#FD85FF") // For ic_card_rabbit
            6 -> Color.parseColor("#FF6376") // For ic_card_fox
            7 -> Color.parseColor("#4E95FF") // For ic_card_water
            8 -> Color.parseColor("#6FBAFF") // For ic_card_penguin
            9 -> Color.parseColor("#46F8A2") // For ic_card_dog
            10 -> Color.parseColor("#9B6CFF") // For ic_card_mouse
            11 -> Color.parseColor("#AF9BFF") // For ic_card_panda
            else -> Color.parseColor("#90FFDE") // 기본값
        }
    }

    fun getFrontImageResId(position: Int): Int {
        return when (position % 5) {
            0 -> R.drawable.ic_front_balance_background_1
            1 -> R.drawable.ic_front_balance_background_2
            2 -> R.drawable.ic_front_balance_background_3
            3 -> R.drawable.ic_front_balance_background_4
            4 -> R.drawable.ic_front_balance_background_5
            else -> R.drawable.ic_front_balance_background_1 // 기본 값
        }
    }

    fun getBackImageResId(position: Int): Int {
        return when (position % 5) {
            0 -> R.drawable.ic_back_balance_background_1
            1 -> R.drawable.ic_back_balance_background_2
            2 -> R.drawable.ic_back_balance_background_3
            3 -> R.drawable.ic_back_balance_background_4
            4 -> R.drawable.ic_back_balance_background_5
            else -> R.drawable.ic_back_balance_background_1 // 기본 값
        }
    }

    fun getVersusColor(context: Context, position: Int): Int {
        return when (position % 5) {
            0 -> ContextCompat.getColor(context, com.teumteum.base.R.color.graphic_pink)
            1 -> ContextCompat.getColor(context, com.teumteum.base.R.color.graphic_tmtm_mintblue)
            2 -> ContextCompat.getColor(context, com.teumteum.base.R.color.graphic_green)
            3 -> ContextCompat.getColor(context, com.teumteum.base.R.color.graphic_tmtm_purple)
            4 -> ContextCompat.getColor(context, com.teumteum.base.R.color.graphic_yellow)
            else -> ContextCompat.getColor(context, com.teumteum.base.R.color.graphic_pink)
        }
    }
}