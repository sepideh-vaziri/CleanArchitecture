package com.architecture.data.entity

import java.io.Serializable

class EmployeeEntity : Serializable {

    var id: Long? = null
    var employee_name: String? = null
    var employee_salary: Int = 0
    var employee_age: Int = 0

}