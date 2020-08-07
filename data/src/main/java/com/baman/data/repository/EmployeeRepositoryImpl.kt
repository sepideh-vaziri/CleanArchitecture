package com.baman.data.repository

import com.baman.data.entity.mapper.EmployeeEntityDataMapper
import com.baman.data.network.ApiService
import com.baman.domain.entity.Employee
import com.baman.domain.entity.base.ServerResponse
import com.baman.domain.repository.EmployeeRepository
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