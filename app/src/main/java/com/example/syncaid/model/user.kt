package com.example.syncaid.model


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class User(

    val _id: String,
    val FirstName: String,
    val LastName: String,
    val Tel: String,
    val Email: String,
    val ProfilePhoto: String,
    val Token:String,

    )

