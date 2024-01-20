package com.teumteum.base.component.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.teumteum.base.component.compose.theme.TmTypo
import com.teumteum.base.component.compose.theme.TmtmColorPalette

@Composable
fun <T> TmTabRow(
    modifier: Modifier = Modifier,
    tabItemList : List<T>,
    tabContent : @Composable (T) -> Unit,
){
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center
    ) {
        tabItemList.forEach{ item ->
            Box(
                modifier = Modifier
                    .weight(1f) // 여기에 weight 적용
                    .height(48.dp)
            ) {
                tabContent(item)
            }
        }
    }
}

@Composable
fun TmTabItem(
    text: String,
    isSelected: Boolean = true,
    onSelect: ()-> Unit
){
    Box(
        modifier = Modifier
            .height(48.dp)
            .fillMaxWidth()
            .clickable { onSelect() }
    ){
        Text(
            modifier = Modifier.align(Alignment.Center).padding(horizontal = 12.dp),
            text = text,
            style = TmTypo.current.HeadLine6,
            color =  if(isSelected) TmtmColorPalette.current.color_text_headline_primary else TmtmColorPalette.current.color_text_body_teritary
        )
        if(isSelected)
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(2.dp)
                    .align(Alignment.BottomCenter),
                color = TmtmColorPalette.current.color_outline_level04_active
            )

    }
}