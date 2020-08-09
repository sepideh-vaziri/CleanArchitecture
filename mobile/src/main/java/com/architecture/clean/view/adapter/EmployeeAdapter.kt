package com.architecture.clean.view.adapter

import android.content.Context
import android.view.View
import com.architecture.clean.R
import com.architecture.clean.view.adapter.base.BaseRecyclerAdapter
import com.architecture.clean.view.item.EmployeeItem
import com.architecture.clean.view.item.base.BaseItem
import com.architecture.presentation.model.EmployeeModel

class EmployeeAdapter(context: Context) : BaseRecyclerAdapter<EmployeeModel>(context) {

    override fun getItemResId(viewType: Int): Int {
        return R.layout.item_employee
    }

    override fun getItemViewHolder(view: View): BaseItem<EmployeeModel> {
        return EmployeeItem(view)
    }

}