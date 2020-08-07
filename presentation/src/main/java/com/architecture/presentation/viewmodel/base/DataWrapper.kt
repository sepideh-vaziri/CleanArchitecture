package com.architecture.presentation.viewmodel.base

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
</T> */
data class DataWrapper<out T> constructor(
    val status: Status,
    val data: T?,
    val serverMessage: String?,
    val hasMore: Boolean,
    val error: Throwable?
) {

    companion object {

        fun <T> success(data: T?): DataWrapper<T> {
            return DataWrapper(Status.SUCCESS, data, null, false, null)
        }

        fun <T> success(data: T?, serverMessage: String?): DataWrapper<T> {
            return DataWrapper(Status.SUCCESS, data, serverMessage, false, null)
        }

        fun <T> success(data: T?, hasMore: Boolean): DataWrapper<T> {
            return DataWrapper(Status.SUCCESS, data, null, hasMore, null)
        }

        fun <T> error(error: Throwable): DataWrapper<T> {
            return DataWrapper(Status.ERROR, null, null, false, error)
        }

        fun <T> error(error: Throwable, serverMessage: String?): DataWrapper<T> {
            return DataWrapper(Status.ERROR, null, serverMessage, false, error)
        }

        fun <T> loading(): DataWrapper<T> {
            return DataWrapper(Status.LOADING, null, null, false, null)
        }

    }

    //******************************************************************************************************************
    internal fun isSuccess() : Boolean {
        return status == Status.SUCCESS
    }

}