package com.teumteum.teumteum.util.callback

import com.teumteum.teumteum.util.custom.view.model.Interest

interface OnDeletedInterests {
    fun deletedInterests(deletedInterests: MutableList<Interest>)
}