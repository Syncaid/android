package com.example.syncaid.view.shared

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.syncaid.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar

class recoverPassword : AppCompatActivity() {

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