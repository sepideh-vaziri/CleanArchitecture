package com.baman.example.di.module

import com.baman.data.repository.EmployeeRepositoryImpl
import com.baman.domain.repository.EmployeeRepository
import dagger.Binds
import dagger.Module

@Module
abstract class EmployeeModule {

    @Binds
    abstract fun provideChallengeRepository(
        challengeRepositoryImpl: EmployeeRepositoryImpl
    ) : EmployeeRepository

}