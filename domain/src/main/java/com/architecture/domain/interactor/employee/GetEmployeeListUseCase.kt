package com.architecture.domain.interactor.employee

import com.architecture.domain.entity.Employee
import com.architecture.domain.entity.base.ServerResponse
import com.architecture.domain.interactor.UseCase
import com.architecture.domain.repository.EmployeeRepository
import javax.inject.Inject

class GetEmployeeListUseCase @Inject constructor(
    private val employeeRepository: EmployeeRepository
) : UseCase<ServerResponse<List<Employee>>, Void>() {

    override suspend fun executeOnBackground(params: Void?): ServerResponse<List<Employee>> {
        return employeeRepository.getEmployeeList()
    }

}