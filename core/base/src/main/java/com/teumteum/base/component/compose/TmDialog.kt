package com.teumteum.base.component.compose

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.teumteum.base.component.compose.TmMarginVerticalSpacer
import com.teumteum.base.component.compose.theme.TmTypo
import com.teumteum.base.component.compose.theme.TmtmColorPalette

@Composable
fun TmDialog(
    title : String,
    okText : String = "확인",
    cancelText : String = "취소하기",
    okColor : Color = TmtmColorPalette.current.color_button_active,
    cancelColor : Color = TmtmColorPalette.current.color_text_button_alternative,
    onOk : () -> Unit,
    onCancel : () -> Unit,
    onDismiss : () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss
    ) {
        Card(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = TmtmColorPalette.current.elevation_color_elevation_level01,
                contentColor = TmtmColorPalette.current.color_text_headline_primary
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(start = 20.dp, end = 20.dp, top = 40.dp, bottom = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = title,
                    style = TmTypo.current.HeadLine6,
                    color = TmtmColorPalette.current.color_text_headline_primary
                )
                TmMarginVerticalSpacer(size = 28)
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ){
                    OutlinedButton(
                        modifier = Modifier
                            .weight(1f)
                            .height(56.dp),
                        onClick = onCancel,
                        colors = ButtonDefaults.buttonColors(containerColor = cancelColor),
                        border = BorderStroke(1.dp, cancelColor),
                        shape = RoundedCornerShape(size = 4.dp)
                    ) {
                        Text(text = cancelText, style = TmTypo.current.HeadLine6, color = TmtmColorPalette.current.color_text_button_alternative)
                    }

                    OutlinedButton(
                        modifier = Modifier
                            .weight(1f)
                            .height(56.dp),
                        onClick = onOk,
                        colors = ButtonDefaults.buttonColors(containerColor = okColor),
                        border = BorderStroke(1.dp, okColor),
                        shape = RoundedCornerShape(size = 12.dp)
                    ) {
                        Text(text = okText, style = TmTypo.current.HeadLine6, color = TmtmColorPalette.current.color_text_button_primary_default)
                    }
                }
            }
        }
    }
}