package com.teumteum.base.component.compose.theme

import androidx.annotation.StringRes
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.teumteum.base.component.compose.TmMarginVerticalSpacer

@Composable
fun TmInputField(
    @StringRes text:Int,
    @StringRes text_error: Int? = null,
    value:String = "",
    onValueChange: (String) -> Unit = {},
    isError:Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused = interactionSource.collectIsFocusedAsState().value

    OutlinedTextField(
        modifier = modifier
            .then(Modifier.fillMaxWidth())
            .height(54.dp),
        value = value,
        onValueChange = onValueChange,
        interactionSource = interactionSource,
        placeholder = {
            Text(
                text= stringResource(text),
                color= TmtmColorPalette.current.color_text_body_quinary,
                style = TmTypo.current.Body1,
            )
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = TmtmColorPalette.current.color_text_body_primary,
            focusedBorderColor = if(isError)TmtmColorPalette.current.color_outline_level01_error else TmtmColorPalette.current.color_background,
            unfocusedBorderColor = if(isError) TmtmColorPalette.current.color_outline_level01_error else TmtmColorPalette.current.color_background,
            unfocusedLabelColor = TmtmColorPalette.current.color_text_body_quinary,
            focusedLabelColor = TmtmColorPalette.current.color_text_body_primary,
            cursorColor = TmtmColorPalette.current.TMTMBlue500,
            backgroundColor = TmtmColorPalette.current.elevation_color_elevation_level01
        ),
        shape = RoundedCornerShape(4.dp),
        visualTransformation = visualTransformation,

        )
    TmMarginVerticalSpacer(size = 8)
    if (isError && text_error != null) {
        Text(
            text = stringResource(text_error),
            color = TmtmColorPalette.current.Error300,
            style = TmTypo.current.Caption2
        )
    }
    TmMarginVerticalSpacer(size = 12)
}