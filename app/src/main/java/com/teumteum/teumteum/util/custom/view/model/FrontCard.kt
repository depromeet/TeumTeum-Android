package com.teumteum.teumteum.util.custom.view.model

import com.teumteum.teumteum.R

data class FrontCard(
    var name: String? = "",
    var company: String? = "",
    var job: String? = "",
    var level: String? = "",
    var area: String? = "",
    var mbti: String? = "",
    var characterResId: Int? = null,
    var floatResId: Int = R.drawable.ic_card_float,
    var editNameResId: Int = R.drawable.ic_card_edit,
    var editCompanyResId: Int = R.drawable.ic_card_edit,
    var editJobResId: Int = R.drawable.ic_card_edit,
    var editAreaResId: Int = R.drawable.ic_card_edit,
)
