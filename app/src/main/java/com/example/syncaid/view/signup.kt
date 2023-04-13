package com.example.syncaid.view

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
import java.util.*

class signup : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        val contextView = findViewById<View>(R.id.view)
        val firstname = findViewById<EditText>(R.id.textInputEditText)
        val lastname = findViewById<EditText>(R.id.textInputEditText2)
        val email = findViewById<EditText>(R.id.textInputEditText3)
        val password = findViewById<EditText>(R.id.textInputEditText4)
        val singup = findViewById<MaterialButton>(R.id.signup)
        val googleSignup = findViewById<MaterialButton>(R.id.googlesignup)

        singup.setOnClickListener {
            if (firstname.text.toString().isEmpty() ||
                lastname.text.toString().isEmpty() ||
                email.text.toString().isEmpty() ||
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
                        // signup logic
                        startActivity(Intent(this,signup2::class.java))
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
