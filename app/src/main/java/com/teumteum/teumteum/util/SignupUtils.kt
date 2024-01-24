package com.teumteum.teumteum.util

import com.teumteum.teumteum.R

object SignupUtils {
    const val STATUS_WORKER = "직장인"
    const val STATUS_STUDENT = "학생"
    const val STATUS_TRAINEE = "취업준비생"

    val CHARACTER_CARD_LIST: HashMap<Int, Int> = hashMapOf(
        0 to R.drawable.ic_card_front_ghost,
        1 to R.drawable.ic_card_front_star,
        2 to R.drawable.ic_card_front_bear,
        3 to R.drawable.ic_card_front_raccon,
        4 to R.drawable.ic_card_front_cat,
        5 to R.drawable.ic_card_front_rabbit,
        6 to R.drawable.ic_card_front_fox,
        7 to R.drawable.ic_card_front_water,
        8 to R.drawable.ic_card_front_penguin,
        9 to R.drawable.ic_card_front_dog,
        10 to R.drawable.ic_card_front_mouse,
        11 to R.drawable.ic_card_front_panda
    )

    const val JOB_DESIGN = "디자인"
    const val JOB_DEVELOPMENT = "개발"
    const val JOB_PLANNING = "기획"

    val JOB_SORT_LIST = arrayListOf(JOB_DESIGN, JOB_DEVELOPMENT, JOB_PLANNING)

    val JOB_DESIGN_LIST = arrayListOf(
        "프로덕트 디자이너", "BX 디자이너", "그래픽 디자이너",
        "영상 디자이너", "UX 디자이너", "UI 디자이너", "플랫폼 디자이너"
    )

    val JOB_DEV_LIST = arrayListOf(
        "BE 개발자", "iOS 개발자", "AOS 개발자", "FE 개발자"
    )

    val JOB_PLAN_LIST = arrayListOf(
        "PO", "PM", "서비스 기획자"
    )

    const val AREA_CITY_SEOUL = "서울"
    const val AREA_CITY_INCHEON = "인천"
    const val AREA_CITY_GYEONGGI = "경기"
}