package com.example.syncaid.model


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class User(

    @SerializedName("_id")  var _id: String,
    @SerializedName("FirstName") var firstName: String,
    @SerializedName("LastName") var lastName: String,
    @SerializedName("Age") var age: String,
    @SerializedName("Gender") var gender: String,
    @SerializedName("Country") var country: String,
    @SerializedName("Tel") var tel: String,
    @SerializedName("Email") var email: String,
    @SerializedName("Password") var password: String,
    @SerializedName("Role") var role: String,
    @SerializedName("ProfilePhoto") var profilePhoto: String,
    @SerializedName("Verified") var verified:String,
    @SerializedName("vString") var vString:String,
    @SerializedName("Token") var token:String,
    @SerializedName("Patients") var patients: Array<miniUser>,
    @SerializedName("Guardians") var guardians: Array<miniUser>

) : Serializable


data class miniUser  (
    @SerializedName("FirstName") var firstName: String,
    @SerializedName("LastName")var lastName: String ,
    @SerializedName("Tel") var tel: String ,
    @SerializedName("Email")var email: String,
    @SerializedName("ProfilePhoto") var  profilePhoto: String ,
) : Serializable