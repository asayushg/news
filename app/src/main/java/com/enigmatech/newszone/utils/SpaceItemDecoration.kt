package com.enigmatech.newszone.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

class SpaceItemDecoration(private var space: Int, private var mSpace: Int) : ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val childCount = parent.childCount
        val itemPosition = parent.getChildAdapterPosition(view)
        val itemCount = state.itemCount
        outRect.left = space
        outRect.right = space

        if (itemPosition == itemCount - 1) {
            outRect.right = mSpace
        }
        if (itemPosition == 0) {
            outRect.left = mSpace
        }

    }
}
