package com.teumteum.teumteum.util.custom.view.model

import com.teumteum.teumteum.R

data class FrontCard(
    var name: String = "디프만",
    var company: String = "@직장,학교명",
    var job: String = "직무명",
    var level: String = "lv.3층",
    var area: String = "선택 지역에 사는",
    var mbti: String = "MBTI",
    var characterResId: Int = R.drawable.ic_card_penguin
)
