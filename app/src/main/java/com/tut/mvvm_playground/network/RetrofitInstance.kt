package com.tut.mvvm_playground.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private var retrofit: Retrofit
    private const val baseUrl = "https://60fd3f631fa9e90017c70dc9.mockapi.io/testApi/"

    init {
        retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getServices(): PersonApi = retrofit.create(PersonApi::class.java)
}