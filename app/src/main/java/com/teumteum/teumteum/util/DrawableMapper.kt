package com.teumteum.teumteum.util

import com.teumteum.teumteum.R

object DrawableMapper {
    fun getCharacterDrawableById(characterId: Int? = 3): Int {
        when (characterId) {
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
        }
        return R.drawable.ic_raccoon
    }
}