package com.example.syncaid.view

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.syncaid.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import org.w3c.dom.Text

class login : AppCompatActivity() {

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
            startActivity(Intent(this,recoverpassword::class.java))
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
                        startActivity(Intent(this,guardhome::class.java))
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