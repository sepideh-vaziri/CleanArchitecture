package com.baman.domain.interactor.employee

import com.baman.domain.entity.Employee
import com.baman.domain.entity.base.ServerResponse
import com.baman.domain.interactor.UseCase
import com.baman.domain.repository.EmployeeRepository
import javax.inject.Inject

class GetEmployeeDetailsUseCase @Inject constructor(
    private val employeeRepository: EmployeeRepository
) : UseCase<ServerResponse<Employee>, GetEmployeeDetailsUseCase.Params>() {

    class Params(val id: Long)

    override suspend fun executeOnBackground(params: Params?): ServerResponse<Employee> {
        return employeeRepository.getEmployeeDetails(params!!.id)
    }

}