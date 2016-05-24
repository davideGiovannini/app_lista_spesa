package com.erindavide.listaspesa.adapters

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Created by demiurgo on 5/24/16.
 */


class GroceryItemDecoration: RecyclerView.ItemDecoration(){


    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.top = PX_MARGIN
        outRect.bottom = PX_MARGIN
    }


    companion object{
        private const val PX_MARGIN = 16
    }
}