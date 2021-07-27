package com.tut.mvvm_playground.network

import com.tut.mvvm_playground.models.Person
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Query

const val PAGE_SIZE = 10

interface PersonApi {
    @GET("users")
    suspend fun getUsers(
        @Query("page") page: Int,
        @Query("limit") limit: Int = PAGE_SIZE
    ): Response<List<Person>>
}