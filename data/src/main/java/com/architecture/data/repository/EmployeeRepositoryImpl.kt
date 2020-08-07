package com.architecture.data.repository

import com.architecture.data.entity.mapper.EmployeeEntityDataMapper
import com.architecture.data.network.ApiService
import com.architecture.domain.entity.Employee
import com.architecture.domain.entity.base.ServerResponse
import com.architecture.domain.repository.EmployeeRepository
import javax.inject.Inject

class EmployeeRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val employeeEntityDataMapper: EmployeeEntityDataMapper
) : EmployeeRepository() {

    //**********************************************************************************************
    override suspend fun getEmployeeList(): ServerResponse<List<Employee>> {
        val serverResponseEntity = apiService.getEmployeeList()

        return employeeEntityDataMapper.transformList(serverResponseEntity)
    }

    //**********************************************************************************************
    override suspend fun getEmployeeDetails(id: Long): ServerResponse<Employee> {
        val serverResponseEntity =apiService.getEmployeeDetails(id)

        return employeeEntityDataMapper.transform(serverResponseEntity)
    }

}