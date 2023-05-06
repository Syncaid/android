package com.example.syncaid.view.shared

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.syncaid.R
import com.example.syncaid.view.patient.patientNav
import com.example.syncaid.viewmodel.userViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.google.gson.JsonObject
import org.json.JSONObject

class recoverPassword : AppCompatActivity() {


    val UserViewModel = userViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recoverpassword)
        val contextView = findViewById<View>(R.id.view)
        val email = findViewById<TextView>(R.id.recoverfield)
        val recoverBtn = findViewById<MaterialButton>(R.id.recoverbtn)

        recoverBtn.setOnClickListener {
            if(email.text.toString().isEmpty()) {
                Snackbar.make(contextView, "Please enter the E-mail associated to your account", Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(ContextCompat.getColor(this, R.color.red_accent))
                    .setTextColor(ContextCompat.getColor(this, R.color.white))
                    .show()
            }
            else {
                val emailLowerCase = email.text.toString().trim().lowercase()
                if (android.util.Patterns.EMAIL_ADDRESS.matcher(emailLowerCase).matches() ) {


                    val jsonObject = JsonObject().apply {
                        addProperty("Email", email.text.toString())
                    }

                    UserViewModel.recoverPassword1(jsonObject) { response, code ->
                        Log.d("res",response.toString())
                        if (code == 200) {
                            Log.d("SUCCESS", "Response: $response")


                            val jsonResponse = JSONObject(response.toString())
                            val otp = jsonResponse.getString("OTP")
                            val intent = Intent(this, recoverPassword2::class.java)
                            intent.putExtra("OTP","$otp")
                            intent.putExtra("EMAIL","$emailLowerCase")
                            startActivity(intent)


                        } else {
                            Snackbar.make(contextView, "Account with given E-mail doesnt exist", Snackbar.LENGTH_SHORT)
                                .setBackgroundTint(ContextCompat.getColor(this, R.color.red_accent))
                                .setTextColor(ContextCompat.getColor(this, R.color.white))
                                .show()
                            Log.e("ERROR", "Error: API call failed")
                        }
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