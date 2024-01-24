package com.teumteum.teumteum.util.custom.view.model

import com.teumteum.teumteum.R

data class BackCard(
    var goalTitle: String = "GOAL",
    var goalContent: String = "가고싶은 회사에 취업해 프로덕트 디자이너로 일하고 싶어요 안녕하세요 저는 사십구자에요",
    var interests: MutableList<String> = mutableListOf("#사이드 프로젝트","#네트워킹","#모여서 각자 일하기"),
    var characterResId: Int = R.drawable.ic_card_back_penguin,
    var floatResId: Int = R.drawable.ic_card_float,
    var editGoalContentResId: Int = R.drawable.ic_card_edit,
)
