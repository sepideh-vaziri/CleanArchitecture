package com.architecture.clean.view.item.base

import android.content.Context
import android.util.DisplayMetrics
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.recyclerview.widget.RecyclerView

abstract class BaseItem<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {

    abstract fun setContent(context: Context, contentObject: T?)
    abstract fun loading(isVisible: Boolean)

    open fun setVisibilityOfLoadingItem(isVisible: Boolean) {}

    //**********************************************************************************************
    protected open fun getWidthPercent() : Int {
        return 100
    }

    internal fun setWidthSize(context: Context) {
        val displayMetrics = DisplayMetrics()
        val windowManager = context.applicationContext
            .getSystemService(Context.WINDOW_SERVICE) as WindowManager

        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val deviceWidth = displayMetrics.widthPixels

        if (getWidthPercent() != 100) {
            val params = itemView.layoutParams as ViewGroup.LayoutParams
            params.width = (deviceWidth / 100) * getWidthPercent()
            itemView.layoutParams = params
        }
    }


}