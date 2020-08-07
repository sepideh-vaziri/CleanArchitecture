package com.baman.example.di.component

import com.baman.example.di.ActivityScope
import com.baman.example.di.module.EmployeeModule
import com.baman.presentation.viewmodel.employee.EmployeeListViewModel
import com.baman.presentation.viewmodel.employee.EmployeeViewModel
import dagger.Component

@ActivityScope
@Component(
    dependencies = [ApplicationComponent::class],
    modules = [EmployeeModule::class]
)
interface EmployeeComponent {

    fun challengeListViewModel() : EmployeeListViewModel

    fun challengeViewModel() : EmployeeViewModel

}