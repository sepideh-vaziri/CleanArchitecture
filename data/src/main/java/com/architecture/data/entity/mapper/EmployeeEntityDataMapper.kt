package com.architecture.data.entity.mapper

import com.architecture.data.entity.EmployeeEntity
import com.architecture.data.entity.server.ServerResponseEntity
import com.architecture.domain.entity.Employee
import com.architecture.domain.entity.base.ServerResponse
import javax.inject.Inject

class EmployeeEntityDataMapper @Inject constructor() {

    /**
     * Transform [ServerResponseEntity] contains [EmployeeEntity] into [ServerResponse] contains [Employee]
     *
     * @param serverResponseEntity the object will be transformed.
     * @return [ServerResponse] if valid [ServerResponseEntity] otherwise null.
     */
    fun transform(serverResponseEntity: ServerResponseEntity<EmployeeEntity>) : ServerResponse<Employee> {
        val serverResponse = ServerResponse<Employee>()

        serverResponse.data = if (serverResponseEntity.data != null) {
            transform(serverResponseEntity.data!!)
        }
        else {
            null
        }

        serverResponse.status = serverResponseEntity.status
        serverResponse.message = serverResponseEntity.message

        return serverResponse
    }

    /**
     * Transform [ServerResponseEntity] contains [EmployeeEntity] into [ServerResponse] contains [Employee]
     *
     * @param serverResponseEntity the object will be transformed.
     * @return [ServerResponse] if valid [ServerResponseEntity] otherwise null.
     */
    fun transformList(
        serverResponseEntity: ServerResponseEntity<List<EmployeeEntity>>
    ) : ServerResponse<List<Employee>> {
        val serverResponse = ServerResponse<List<Employee>>()

        serverResponse.data = if (serverResponseEntity.data != null) {
            transform(serverResponseEntity.data!!)
        }
        else {
            null
        }

        serverResponse.status = serverResponseEntity.status
        serverResponse.message = serverResponseEntity.message

        return serverResponse
    }

    /**
     * Transform list of [EmployeeEntity] into list of [Employee]
     *
     * @param employeeEntityList List of object will be transformed.
     * @return List of [Employee] if valid [EmployeeEntity] otherwise null.
     */
    fun transform(employeeEntityList: List<EmployeeEntity>?) : List<Employee> {
        val employeeList: MutableList<Employee> = ArrayList()

        if (employeeEntityList == null) {
            return employeeList
        }

        for (employeeEntity in employeeEntityList) {
            val employee = transform(employeeEntity)

            if (employee != null) {
                employeeList.add(employee)
            }
        }

        return employeeList
    }

    /**
     * Transform a [EmployeeEntity] into an [Employee]
     *
     * @param employeeEntity Object to be transformed.
     * @return [Employee] if valid [EmployeeEntity] otherwise null.
     */
    fun transform(employeeEntity: EmployeeEntity?): Employee? {
        if (employeeEntity == null) {
            return null
        }

        val employee = Employee()

        employee.id = employeeEntity.id
        employee.employee_name = employeeEntity.employee_name
        employee.employee_salary = employeeEntity.employee_salary
        employee.employee_age = employeeEntity.employee_age

        return employee
    }
    
}