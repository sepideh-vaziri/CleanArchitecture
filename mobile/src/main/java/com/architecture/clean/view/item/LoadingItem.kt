package com.architecture.clean.view.item

import android.content.Context
import android.view.View
import com.architecture.clean.base.hide
import com.architecture.clean.base.show
import com.architecture.clean.view.item.base.BaseItem
import kotlinx.android.synthetic.main.item_loading.view.*

class LoadingItem<T>(itemView: View) : BaseItem<T>(itemView) {

    override fun setContent(context: Context, contentObject: T?) {
        //Nothing
    }

    override fun setVisibilityOfLoadingItem(isVisible: Boolean) {

        if (isVisible) {
            itemView.text_loading.show()
        }
        else {
            itemView.text_loading.hide()
        }

    }

    override fun loading(isVisible: Boolean) {}

}