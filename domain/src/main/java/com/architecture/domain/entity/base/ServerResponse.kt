package com.architecture.domain.entity.base

class ServerResponse<T> {

    companion object {
        const val SUCCESS_STATUS = "success"
    }

    var data: T? = null
    var status: String? = null
    var message: String? = null

    fun isSuccess() : Boolean {
        return status == status
    }

}
