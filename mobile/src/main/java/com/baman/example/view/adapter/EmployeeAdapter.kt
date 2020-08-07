package com.baman.example.view.adapter

import android.content.Context
import android.view.View
import com.baman.example.R
import com.baman.example.view.adapter.base.BaseRecyclerAdapter
import com.baman.example.view.item.EmployeeItem
import com.baman.example.view.item.base.BaseItem
import com.baman.presentation.model.EmployeeModel

class EmployeeAdapter(val context: Context) : BaseRecyclerAdapter<EmployeeModel>(context) {

    override fun getItemResId(viewType: Int): Int {
        return R.layout.item_employee
    }

    override fun getItemViewHolder(view: View): BaseItem<EmployeeModel> {
        return EmployeeItem(view)
    }

}