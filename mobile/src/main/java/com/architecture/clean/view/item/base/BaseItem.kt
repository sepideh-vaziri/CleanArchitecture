package com.architecture.clean.view.item.base

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseItem<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {

    abstract fun setContent(context: Context, contentObject: T?)
    abstract fun loading(isVisible: Boolean)

    open fun setVisibilityOfLoadingItem(isVisible: Boolean) {}

}