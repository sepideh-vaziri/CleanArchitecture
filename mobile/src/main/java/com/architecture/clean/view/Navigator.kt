package com.architecture.clean.view

import android.content.Context
import com.architecture.clean.view.activity.employee.EmployeeDetailsActivity
import com.architecture.presentation.model.EmployeeModel
import javax.inject.Inject

class Navigator @Inject constructor() {


    //**********************************************************************************************
    internal fun openEmployeeDetails(context: Context, employeeModel: EmployeeModel) {
        context.startActivity(
            EmployeeDetailsActivity.getIntent(context, employeeModel)
        )
    }

}