package com.teumteum.teumteum.util.custom.itemdecoration

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexboxLayoutManager
import com.teumteum.teumteum.util.extension.dpToPx

class FlexboxItemDecoration(private val context: Context, private val leftSpaceDp: Int, private val topSpaceDp: Int) : RecyclerView.ItemDecoration() {

    private val leftSpacePx = leftSpaceDp.dpToPx(context)
    private val topSpacePx = topSpaceDp.dpToPx(context)

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val layoutManager = parent.layoutManager as? FlexboxLayoutManager
        val position = parent.getChildAdapterPosition(view)

        layoutManager?.let {
            val isFirstInRow = position % it.flexWrap == 0

            // 왼쪽에 아무것도 없는 첫 번째 아이템은 왼쪽 마진을 0으로 설정
            if (isFirstInRow) {
                outRect.left = 0
            } else {
                outRect.left = leftSpacePx
            }

            // 상단 마진은 항상 동일하게 적용
            outRect.top = topSpacePx

            // 오른쪽 마진은 항상 동일하게 적용
            outRect.right = leftSpacePx
        }
    }
}