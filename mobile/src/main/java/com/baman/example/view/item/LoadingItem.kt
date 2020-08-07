package com.baman.example.view.item

import android.content.Context
import android.view.View
import com.baman.example.base.hide
import com.baman.example.base.show
import com.baman.example.view.item.base.BaseItem
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