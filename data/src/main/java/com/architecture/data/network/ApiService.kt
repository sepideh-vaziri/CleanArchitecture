package com.architecture.data.network

import com.architecture.data.entity.*
import com.architecture.data.entity.server.ServerResponseEntity
import retrofit2.Call
import retrofit2.http.*


interface ApiService {

    //Authentication *******************************************************************************
    @POST("oauth2/token")
    fun refreshToken(
        @Body authorizationEntity: AuthorizationEntity
    ) : Call<ServerResponseEntity<String>>

    //Employee *************************************************************************************
    @GET("v1/employees")
    suspend fun getEmployeeList() : ServerResponseEntity<List<EmployeeEntity>>

    @GET("v1/employee/{id}")
    suspend fun getEmployeeDetails(
        @Path("id") id: Long
    ) : ServerResponseEntity<EmployeeEntity>

}