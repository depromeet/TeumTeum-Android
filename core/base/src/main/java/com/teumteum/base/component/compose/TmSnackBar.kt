package com.teumteum.base.component.compose

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.ui.Alignment
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.rememberCoroutineScope
import com.teumteum.base.component.compose.theme.TmtmColorPalette
import androidx.compose.material.*
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.launch


@Composable
fun CustomSnackbar(
    text: String,
) {
    val snackbarHostState = remember { SnackbarHostState()}
    val coroutineScope = rememberCoroutineScope()
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .clickable {
                coroutineScope.launch {
                    // snackBar의 결과를 받을 수 있음
                    // 이 결과는 스낵바가 닫아졌는지? 스낵바의 버튼이 눌러졌는지?
                    snackbarHostState
                        .showSnackbar(
                            "snackBar show!!", "확인", SnackbarDuration.Short // 스낵바 보여주는 시간
                        )
                        .let {
                            when (it) {
                                SnackbarResult.Dismissed ->
                                    Log.d("snackBar", "snackBar: 스낵바 닫아짐")

                                // 스낵바에 있는 버튼이 눌러졌을 때 로직처리 하는 부분
                                SnackbarResult.ActionPerformed ->
                                    Log.d("snackBar", "snackBar: 확인 버튼 눌러짐")

                            }
                        }
                }
            }
            .background(
                color = TmtmColorPalette.current.color_text_button_primary_default02,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(horizontal = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = TmtmColorPalette.current.color_text_body_quinary
        )

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }

}
