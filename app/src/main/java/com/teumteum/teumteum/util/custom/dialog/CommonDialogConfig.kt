package com.teumteum.teumteum.util.custom.dialog

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CommonDialogConfig(
    val image: Int? = null,
    val title: String,
    val description: String? = null,
    val negativeButtonText: String? = null,
    val positiveButtonText: String,
) : Parcelable
