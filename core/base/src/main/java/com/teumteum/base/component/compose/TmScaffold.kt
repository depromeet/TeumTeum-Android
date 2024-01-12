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
    topbarText: String,
    onClick: (() -> Unit)? = null,
    content: @Composable (PaddingValues) -> Unit,
) {
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
                    navigationIconContentColor = TmtmColorPalette.current.color_icon_level01,
                    actionIconContentColor = TmtmColorPalette.current.color_background
                ),
                navigationIcon = {
                    IconButton(
                        onClick = { if (onClick != null) { onClick() } },
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_arrow_left_l),
                            contentDescription = "Localized description",
                        )
                    }
                },
                actions = {
                        IconButton(
                            onClick = { if (onClick != null) { onClick() } },
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_arrow_left_l),
                                contentDescription = "Localized description",
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