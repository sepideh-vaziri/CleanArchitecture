package com.architecture.domain.interactor.employee

import com.architecture.domain.entity.Employee
import com.architecture.domain.entity.base.ServerResponse
import com.architecture.domain.interactor.UseCase
import com.architecture.domain.repository.EmployeeRepository
import javax.inject.Inject

class GetEmployeeDetailsUseCase @Inject constructor(
    private val employeeRepository: EmployeeRepository
) : UseCase<ServerResponse<Employee>, GetEmployeeDetailsUseCase.Params>() {

    class Params(val id: Long)

    override suspend fun executeOnBackground(params: Params?): ServerResponse<Employee> {
        return employeeRepository.getEmployeeDetails(params!!.id)
    }

}