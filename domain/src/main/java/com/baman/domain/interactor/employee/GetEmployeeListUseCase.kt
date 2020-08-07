package com.baman.domain.interactor.employee

import com.baman.domain.entity.Employee
import com.baman.domain.entity.base.ServerResponse
import com.baman.domain.interactor.UseCase
import com.baman.domain.repository.EmployeeRepository
import javax.inject.Inject

class GetEmployeeListUseCase @Inject constructor(
    private val employeeRepository: EmployeeRepository
) : UseCase<ServerResponse<List<Employee>>, Void>() {

    override suspend fun executeOnBackground(params: Void?): ServerResponse<List<Employee>> {
        return employeeRepository.getEmployeeList()
    }

}