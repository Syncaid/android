package com.example.syncaid.network

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query


interface apiService {
    @POST("/user/register")
    fun register(@Body requestBody: JsonObject): Call<JsonElement>

    @PUT("/user/update/{userId}")
    fun register2(@Path("userId") userId: String, @Body requestBody: JsonObject): Call<JsonElement>

    @POST("/user/login")
    fun login( @Body requestBody: JsonObject): Call<JsonElement>




}