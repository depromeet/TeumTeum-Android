package com.teumteum.teumteum.util.custom.itemdecoration

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexboxLayoutManager
import com.teumteum.teumteum.util.extension.dpToPx

class FlexboxItemDecoration(private val context: Context, private val horizontalSpaceDp: Int, private val verticalSpaceDp: Int) : RecyclerView.ItemDecoration() {

    private val horizontalSpacePx = horizontalSpaceDp.dpToPx(context)
    private val verticalSpacePx = verticalSpaceDp.dpToPx(context)

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val layoutManager = parent.layoutManager as? FlexboxLayoutManager
        val position = parent.getChildAdapterPosition(view)

        layoutManager?.let {
            val itemCount = parent.adapter?.itemCount ?: 0
            val isLastRowItem = position >= itemCount - it.flexWrap
            val isFirstInRow = position % it.flexWrap == 0

            // 왼쪽에 아무것도 없는 첫 번째 아이템은 왼쪽 마진을 0으로 설정
            if (isFirstInRow) {
                outRect.left = 0
            } else {
                outRect.left = horizontalSpacePx
            }

            // 마지막 줄의 아이템은 아래 마진을 0으로 설정
            if (isLastRowItem) {
                outRect.bottom = 0
            } else {
                outRect.bottom = verticalSpacePx
            }

            // 상단 마진은 항상 동일하게 적용
            outRect.top = verticalSpacePx

            // 오른쪽 마진은 항상 동일하게 적용
            outRect.right = horizontalSpacePx
        }
    }
}