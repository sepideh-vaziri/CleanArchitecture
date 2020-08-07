package com.seva.practicalview.customview

import android.content.Context
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class WrapLinearLayoutManager(
    context: Context,
    @RecyclerView.Orientation orientation: Int,
    reverseLayout: Boolean
) : LinearLayoutManager(context, orientation, reverseLayout) {

    companion object {
        private val TAG = WrapLinearLayoutManager::class.java.simpleName
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {

        try {
            super.onLayoutChildren(recycler, state)
        }
        catch (e: IndexOutOfBoundsException) {
            Log.e(TAG, "onLayoutChildren: ", e)
        }

    }

    override fun supportsPredictiveItemAnimations(): Boolean {
        return false //Todo
    }
}