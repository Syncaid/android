package com.example.syncaid.view.shared

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.PixelFormat
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.os.Handler
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
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
        val progressBar = findViewById<ProgressBar>(R.id.progress)



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
                    if(password.text.toString().length >= 8)
                    {

                        progressBar.setVisibility(View.VISIBLE);

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
                                val role = jsonResponse.getString("Role")
                                val verified = jsonResponse.getBoolean("Verified")
                                val id = jsonResponse.getString("_id")




                                if (verified) {
                                    val editor = sharedPref.edit()
                                    editor.putString("ID", jsonResponse.getString("_id"))
                                    editor.putString("FIRSTNAME", jsonResponse.getString("FirstName"))
                                    editor.putString("LASTNAME", jsonResponse.getString("LastName"))
                                    editor.putString("PROFILEPHOTO", jsonResponse.getString("ProfilePhoto"))
                                    editor.putString("ROLE",jsonResponse.getString("Role"))
                                    editor.putString("TOKEN", jsonResponse.getString("Token"))
                                    editor.putString("EMAIL", jsonResponse.getString("Email"))
                                    editor.putBoolean("VERIFIED", jsonResponse.getBoolean("Verified"))
                                    editor.putBoolean("LOGGEDIN", true)
                                    editor.apply()

                                   if(role == "Patient") {
                                       val intent = Intent(this, patientNav::class.java)

                                       Handler().postDelayed(Runnable {
                                           startActivity(intent)
                                       }, 1000)

                                   }
                                   else {
                                       // nav to guardian home
                                   }
                               }
                                else {
                                   val intent = Intent(this, verifyEmail::class.java)
                                   intent.putExtra("EMAIL",email.text.toString())
                                   intent.putExtra("ID",id.toString())
                                    Handler().postDelayed(Runnable {
                                        startActivity(intent)
                                    }, 1000)
                                }

                            } else {

                                Handler().postDelayed( {
                                    progressBar.setVisibility(View.GONE);
                                    Snackbar.make(contextView, "Wrong login information", Snackbar.LENGTH_SHORT)
                                        .setBackgroundTint(ContextCompat.getColor(this, R.color.red_accent))
                                        .setTextColor(ContextCompat.getColor(this, R.color.white))
                                        .show()

                                }, 1000)

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