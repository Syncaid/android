package com.example.syncaid.viewmodel

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.example.syncaid.network.RetrofitInstance
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class userViewModel() : ViewModel(){

    // Create a Retrofit instance
    private val retrofit = RetrofitInstance.retrofit

    // Create an instance of your API service interface
    private val apiService = retrofit.create(com.example.syncaid.network.apiService::class.java)

    // Create a function to make the register API call
    fun register(jsonObject: JsonObject, callback: (String?, Int?) -> Unit) {
        apiService.register(jsonObject).enqueue(object : Callback<JsonElement> {
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        // Handle the JSON response
                        callback(responseBody.toString(),response.code())
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    callback(errorBody,response.code())
                }
            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                callback(t.message,501)
            }
        })
    }

    fun register2(id: String, jsonObject: JsonObject, callback: (String?, Int) -> Unit) {
        apiService.register2(id,jsonObject).enqueue(object : Callback<JsonElement> {
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        // Handle the JSON response
                        callback(responseBody.toString(),response.code())
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    callback(errorBody,response.code())
                }
            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                callback(t.message,501)
            }
        })
    }

    fun login(jsonObject: JsonObject, callback: (String?, Int) -> Unit) {
        apiService.login(jsonObject).enqueue(object : Callback<JsonElement> {
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        // Handle the JSON response

                        callback(responseBody.toString(),response.code())
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    callback(errorBody,response.code())
                }
            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                callback(t.message,501)
            }
        })
    }
}