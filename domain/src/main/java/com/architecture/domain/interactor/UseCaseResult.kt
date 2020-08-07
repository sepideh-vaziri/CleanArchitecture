package com.architecture.domain.interactor

sealed class UseCaseResult<out T> {

    data class Success<out T>(val data: T) : UseCaseResult<T>()

    data class Error(val throwable: Throwable) : UseCaseResult<Nothing>()

}