package com.architecture.clean.view.item

import android.content.Context
import android.view.View
import com.architecture.clean.view.item.base.BaseItem
import com.architecture.presentation.model.EmployeeModel
import kotlinx.android.synthetic.main.item_employee.view.*

class EmployeeItem(itemView: View) : BaseItem<EmployeeModel>(itemView) {

    override fun setContent(context: Context, contentObject: EmployeeModel?) {
        if (contentObject == null) {
            return
        }

        itemView.textName.text = contentObject.employee_name
    }

    override fun loading(isVisible: Boolean) {}

}