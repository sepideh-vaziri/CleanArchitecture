package com.baman.example.view.customview

import android.content.Context
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class WrapGridLayoutManager(
    context: Context,
    spanCount: Int,
    @RecyclerView.Orientation orientation: Int,
    reverseLayout: Boolean
) : GridLayoutManager(context, spanCount, orientation, reverseLayout) {

    companion object {
        private val TAG = WrapGridLayoutManager::class.java.simpleName
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