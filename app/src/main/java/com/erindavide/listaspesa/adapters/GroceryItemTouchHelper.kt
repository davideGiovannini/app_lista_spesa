package com.erindavide.listaspesa.adapters

import android.graphics.Canvas
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import com.erindavide.listaspesa.utilities.clamp

/**
 * Created by demiurgo on 5/24/16.
 */



class GroceryItemTouchHelper(val mAdapter: GroceryListAdapter) : ItemTouchHelper.Callback() {

    override fun isLongPressDragEnabled() = true
    override fun isItemViewSwipeEnabled() = true

    override fun getMovementFlags(recyclerView: RecyclerView,
                                  viewHolder: RecyclerView.ViewHolder): Int {
        val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        val swipeFlags = ItemTouchHelper.START or ItemTouchHelper.END
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    override fun onMove(recyclerView: RecyclerView?, source: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        if (source.itemViewType != target.itemViewType) {
            return false;
        }

        // Notify the adapter of the move
        mAdapter.onItemMove(source.adapterPosition, target.adapterPosition);
        return true;
    }


    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, side: Int) {
        //side can be START or END
        // Notify the adapter of the dismissal
        mAdapter.onItemDismiss(viewHolder.adapterPosition);
    }


    override fun onChildDraw(canvas: Canvas,
                             recyclerView: RecyclerView,
                             viewHolder: RecyclerView.ViewHolder,
                             dX: Float, dY: Float,
                             actionState: Int,
                             isCurrentlyActive: Boolean) {
        when (actionState) {
            ItemTouchHelper.ACTION_STATE_SWIPE -> {
                // Fade out the view as it is swiped out of the parent's bounds
                val alpha = 1.0f - Math.abs(dX) / viewHolder.itemView.width.toFloat();
                viewHolder.itemView.alpha = alpha;
                viewHolder.itemView.translationX = dX
            }
            ItemTouchHelper.ACTION_STATE_DRAG -> {
                viewHolder.itemView.translationZ = DRAG_Z_TRANSLATION * dY.clamp()
                viewHolder.itemView.translationY = dY
            }
            else -> {
                super.onChildDraw(canvas, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

            }
        }
    }


    companion object{
        private const val DRAG_Z_TRANSLATION = 8f
    }
}