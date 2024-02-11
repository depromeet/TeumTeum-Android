package com.teumteum.teumteum.presentation.familiar.shaketopic.topic.model

import android.graphics.drawable.Drawable

data class Topic(
    val topicNumber: String? = "",
    val topicTitle: String? = "",
    val frontImage: Drawable? = null,
    val backImage: Drawable? = null
)