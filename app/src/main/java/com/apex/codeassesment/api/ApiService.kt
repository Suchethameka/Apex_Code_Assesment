package com.apex.codeassesment.api

import com.apex.codeassesment.data.model.UserResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("api")
    suspend fun getUser(): UserResponse

    @GET("api?results=10")
    suspend fun getUsers(): Response<UserResponse>
}