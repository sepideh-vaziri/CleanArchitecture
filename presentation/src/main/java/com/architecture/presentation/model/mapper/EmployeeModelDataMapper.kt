package com.architecture.presentation.model.mapper

import com.architecture.domain.entity.Employee
import com.architecture.presentation.model.EmployeeModel
import javax.inject.Inject

class EmployeeModelDataMapper @Inject constructor() {

    /**
     * Transform list of [Employee] into an [EmployeeModel]
     *
     * @param employeeList List of object will be transformed.
     * @return List of [EmployeeModel] if valid [Employee] otherwise null.
     */
    fun transform(employeeList: List<Employee>?) : List<EmployeeModel> {
        val employeeModelList: MutableList<EmployeeModel> = ArrayList()

        if (employeeList == null) {
            return employeeModelList
        }

        for (employee in employeeList) {
            val employeeModel = transform(employee)

            if (employeeModel != null) {
                employeeModelList.add(employeeModel)
            }
        }

        return employeeModelList
    }

    /**
     * Transform a [Employee] into an [EmployeeModel]
     *
     * @param employee Object to be transformed.
     * @return [EmployeeModel] if valid [Employee] otherwise null.
     */
    fun transform(employee: Employee?): EmployeeModel? {
        var employeeModel : EmployeeModel? = null

        if (employee != null) {
            employeeModel = EmployeeModel()
            employeeModel.id = employee.id
            employeeModel.employee_name = employee.employee_name
            employeeModel.employee_salary = employee.employee_salary
            employeeModel.employee_age = employee.employee_age
        }

        return employeeModel
    }

}