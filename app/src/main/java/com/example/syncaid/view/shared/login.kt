package com.example.syncaid.view.shared

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.syncaid.R
import com.example.syncaid.view.patient.patientNav
import com.example.syncaid.viewmodel.userViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.google.gson.JsonObject
import org.json.JSONObject

class login : AppCompatActivity() {

    val UserViewModel = userViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)
        val contextView = findViewById<View>(R.id.view)
        val email = findViewById<EditText>(R.id.EmailTextField)
        val password = findViewById<EditText>(R.id.pw)
        val signup = findViewById<MaterialButton>(R.id.login)
        val forgotpass = findViewById<TextView>(R.id.textView7)
        val googleSignup = findViewById<MaterialButton>(R.id.googlesignup)


        forgotpass.setOnClickListener{
            startActivity(Intent(this, recoverPassword::class.java))
        }
        signup.setOnClickListener {
            if (email.text.toString().isEmpty() ||
                password.text.toString().isEmpty()
            ) {
                Snackbar.make(contextView, "All fields are required", Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(ContextCompat.getColor(this, R.color.red_accent))
                    .setTextColor(ContextCompat.getColor(this, R.color.white))
                    .show()
            }
            else {
                val emailLowerCase = email.text.toString().trim().lowercase()
                if (android.util.Patterns.EMAIL_ADDRESS.matcher(emailLowerCase).matches() ) {
                    if(password.text.toString().length >= 8) {
                      // login logic
                        val jsonObject = JsonObject().apply {
                            addProperty("Email", email.text.toString())
                            addProperty("Password", password.text.toString())
                        }

                        UserViewModel.login(jsonObject) { response, code ->

                            if (code == 200) {
                                Log.d("SUCCESS", "Response: $response")


                                val sharedPref = getSharedPreferences("USER_DATA", Context.MODE_PRIVATE)
                                val jsonResponse = JSONObject(response.toString())
                                val id = jsonResponse.getString("_id")
                                val firstname = jsonResponse.getString("FirstName")
                                val lastname = jsonResponse.getString("LastName")
                                val token = jsonResponse.getString("Token")
                                val role = jsonResponse.getString("Role")
                                val verified = jsonResponse.getBoolean("Verified")


                                // add to shared pref

                               if (verified) {
                                   if(role == "Patient") {
                                       val intent = Intent(this, patientNav::class.java)
                                       startActivity(intent)
                                   }
                                   else {
                                       // nav to guardian home
                                   }
                               }
                                else {
                                    // nav to verify email
                                }

                            } else {
                                Snackbar.make(contextView, "Wrong login information", Snackbar.LENGTH_SHORT)
                                    .setBackgroundTint(ContextCompat.getColor(this, R.color.red_accent))
                                    .setTextColor(ContextCompat.getColor(this, R.color.white))
                                    .show()
                                Log.e("ERROR", "Error: API call failed")
                            }
                        }
                    }
                    else
                    {
                        Snackbar.make(contextView, "Password must be at least 8 caracters long", Snackbar.LENGTH_SHORT)
                            .setBackgroundTint(ContextCompat.getColor(this, R.color.red_accent))
                            .setTextColor(ContextCompat.getColor(this, R.color.white))
                            .show()
                    }
                }
                else {
                    Snackbar.make(contextView, "Invalid E-mail address", Snackbar.LENGTH_SHORT)
                        .setBackgroundTint(ContextCompat.getColor(this, R.color.red_accent))
                        .setTextColor(ContextCompat.getColor(this, R.color.white))
                        .show()

                }
            }

        }


    }
}