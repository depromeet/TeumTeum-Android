package com.teumteum.teumteum.presentation.teumteum.shake.model

data class UserInterest(
    var x: Float,
    var y: Float,
    val width: Int,
    val height: Int,
    val color: Int,
    val moveSensitivity: Float,
    var text: String
)
