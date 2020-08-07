package com.architecture.domain.repository

import com.architecture.domain.entity.Employee
import com.architecture.domain.entity.base.ServerResponse

abstract class EmployeeRepository : Repository() {

    abstract suspend fun getEmployeeList() : ServerResponse<List<Employee>>

    abstract suspend fun getEmployeeDetails(id: Long) : ServerResponse<Employee>

}