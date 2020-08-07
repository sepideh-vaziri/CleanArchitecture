package com.baman.data.network

import com.baman.data.entity.*
import com.baman.data.entity.server.ServerResponseEntity
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