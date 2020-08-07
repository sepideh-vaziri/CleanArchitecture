package com.architecture.clean.di.component

import com.architecture.clean.di.ActivityScope
import com.architecture.clean.di.module.EmployeeModule
import com.architecture.presentation.viewmodel.employee.EmployeeListViewModel
import com.architecture.presentation.viewmodel.employee.EmployeeViewModel
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