package com.baman.example.view

import android.content.Context
import com.baman.example.view.activity.employee.EmployeeDetailsActivity
import com.baman.presentation.model.EmployeeModel
import javax.inject.Inject

class Navigator @Inject constructor() {


    //**********************************************************************************************
    internal fun openEmployeeDetails(context: Context, employeeModel: EmployeeModel) {
        context.startActivity(
            EmployeeDetailsActivity.getIntent(context, employeeModel)
        )
    }

}