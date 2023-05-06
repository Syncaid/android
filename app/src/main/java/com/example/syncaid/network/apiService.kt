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

    @POST("/user/passwordemail")
    fun sendPasswordEmail( @Body requestBody: JsonObject): Call<JsonElement>

<<<<<<< HEAD

=======
    @POST("/user/resetpassword")
    fun resetPassword( @Body requestBody: JsonObject): Call<JsonElement>

    @POST("/user/verificationemail")
    fun sendVerificationEmail( @Body requestBody: JsonObject): Call<JsonElement>

    @POST("/user/logout/{userId}")
    fun logout(@Path("userId") userId: String): Call<JsonElement>


    @GET("/faint/byUserId/{id}")
    fun getFaints(@Path("id") id: String): Call<JsonElement>

    @DELETE("/faint/{id}")
    fun deleteFaint(@Path("id") id: String): Call<JsonElement>
>>>>>>> origin/dev


}