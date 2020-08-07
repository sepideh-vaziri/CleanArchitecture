package com.architecture.clean.di.module

import com.architecture.data.repository.EmployeeRepositoryImpl
import com.architecture.domain.repository.EmployeeRepository
import dagger.Binds
import dagger.Module

@Module
abstract class EmployeeModule {

    @Binds
    abstract fun provideChallengeRepository(
        challengeRepositoryImpl: EmployeeRepositoryImpl
    ) : EmployeeRepository

}