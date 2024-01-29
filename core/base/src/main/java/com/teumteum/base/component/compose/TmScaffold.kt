package com.teumteum.base.component.compose


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalMapOf
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.teumteum.base.component.compose.theme.TmTypo
import com.teumteum.base.component.compose.theme.TmtmColorPalette
import com.teumteum.base.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TmScaffold(
    topbarText: String = "",
    onClick: (() -> Unit)? = null,
    onConfirmClick: (() -> Unit)? = null,
    isSetting: Boolean = false,
    isConfirm: Boolean = false,
    content: @Composable (PaddingValues) -> Unit,
) {
    val TmtmColorPalette = TmtmColorPalette

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Spacer(Modifier.weight(1f)) // 첫 번째 Spacer
                        Text(
                            text = topbarText,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = TmTypo.current.HeadLine6,
                            color = TmtmColorPalette.current.color_text_headline_primary,
                            textAlign = TextAlign.Center
                        )
                        Spacer(Modifier.weight(1f)) // 두 번째 Spacer
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = TmtmColorPalette.current.color_background,
                    titleContentColor = TmtmColorPalette.current.color_text_headline_primary,
                    navigationIconContentColor = TmtmColorPalette.current.color_text_headline_primary,
                    actionIconContentColor = TmtmColorPalette.current.color_icon_level01
                ),
                navigationIcon = {
                    IconButton(
                        onClick = { if (onClick != null) { onClick() } },
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_arrow_left_l),
                            contentDescription = "Localized description",
                            tint = if (isSetting) Color.Transparent else TmtmColorPalette.current.color_icon_level01,
                        )
                    }
                },
                actions = {
                        IconButton(
                            onClick = {
                                if(isConfirm) {
                                    if (onConfirmClick != null) {
                                        onConfirmClick()
                                    }
                                }
                                else if(!isConfirm) {
                                    if (onClick != null) { onClick() } }
                                },
                        ) {
                            Icon(
                                painter = if(isSetting)painterResource(R.drawable.ic_setting) else if(isConfirm) painterResource(R.drawable.ic_system_line) else painterResource(R.drawable.ic_setting),
                                contentDescription = "Localized description",
                                tint = if (isSetting) Color.Unspecified else if (isConfirm) TmtmColorPalette.current.color_icon_level01 else Color.Transparent
                            )
                        }
                }
            )
        },
        content = { it->
            content(it)
        }
    )
}