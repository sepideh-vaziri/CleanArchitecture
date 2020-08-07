package com.baman.data.entity.server

import com.baman.domain.entity.base.ServerResponse

class ServerResponseEntity<TEntity> {

    var data: TEntity? = null
    var status: String? = null
    var message: String? = null

    fun isSuccess() : Boolean {
        return status == ServerResponse.SUCCESS_STATUS
    }

}