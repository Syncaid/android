package com.example.syncaid.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

<<<<<<< HEAD
=======

>>>>>>> origin/dev
object RetrofitInstance {
    private val okHttpClient = OkHttpClient().newBuilder()
        .connectTimeout(25, TimeUnit.SECONDS)
        .readTimeout(25, TimeUnit.SECONDS)
        .writeTimeout(25, TimeUnit.SECONDS)
        .build()

    val retrofit: Retrofit = Retrofit.Builder()
<<<<<<< HEAD
        .baseUrl("http://192.168.100.6:9095")
=======
        .baseUrl("http://192.168.1.124:9095")
>>>>>>> origin/dev
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

}
