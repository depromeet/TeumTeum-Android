package com.teumteum.teumteum.util

import com.teumteum.teumteum.R
import timber.log.Timber

object IdMapper {
    fun getCharacterDrawableById(characterId: Int): Int {
        return when (characterId) {
            0 -> R.drawable.ic_ghost
            1 -> R.drawable.ic_star
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

    fun getCardCharacterDrawableById(characterId: Int): Int {
        return when (characterId) {
            0 -> R.drawable.ic_card_ghost
            1 -> R.drawable.ic_card_star
            2 -> R.drawable.ic_card_bear
            3 -> R.drawable.ic_card_raccon
            4 -> R.drawable.ic_card_cat
            5 -> R.drawable.ic_card_rabbit
            6 -> R.drawable.ic_card_fox
            7 -> R.drawable.ic_card_water
            8 -> R.drawable.ic_card_penguin
            9 -> R.drawable.ic_card_dog
            10 -> R.drawable.ic_card_mouse
            11 -> R.drawable.ic_card_panda
            else -> R.drawable.ic_card_raccon //기본값
        }
    }
}