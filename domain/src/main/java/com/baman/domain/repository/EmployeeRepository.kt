package com.baman.domain.repository

import com.baman.domain.entity.Employee
import com.baman.domain.entity.base.ServerResponse

abstract class EmployeeRepository : Repository() {

    abstract suspend fun getEmployeeList() : ServerResponse<List<Employee>>

    abstract suspend fun getEmployeeDetails(id: Long) : ServerResponse<Employee>

}