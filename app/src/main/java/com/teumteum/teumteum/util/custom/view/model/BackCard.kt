package com.teumteum.teumteum.util.custom.view.model

import com.teumteum.teumteum.R

data class BackCard(
    var goalTitle: String = "GOAL",
    var goalContent: String? = null,
    var interests: MutableList<String>? = mutableListOf(),
    var characterResId: Int? = null,
    var floatResId: Int = R.drawable.ic_card_float,
    var editGoalContentResId: Int = R.drawable.ic_card_edit,
)
