package com.example.syncaid.view.shared

<<<<<<< HEAD
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.syncaid.R

class verifyEmail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify_email)
=======
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import com.example.syncaid.R
import com.example.syncaid.viewmodel.userViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.google.gson.JsonObject

class verifyEmail : AppCompatActivity() {

    val UserViewModel = userViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify_email)
        val contextView = findViewById<View>(R.id.view)
        val email = intent.getStringExtra("EMAIL")
        val id = intent.getStringExtra("ID")

        val resend = findViewById<MaterialButton>(R.id.resendemail)
        val leave = findViewById<MaterialButton>(R.id.leave)

        leave.setOnClickListener {
            UserViewModel.logout(id.toString()) { response, code ->
                Log.d("RES", "Response: $response")
                if (code == 200) {
                    Log.d("SUCCESS", "Response: $response")
                    val intent = Intent(this, login::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(intent)

                } else {
                    Snackbar.make(contextView, "E-mail sending failed", Snackbar.LENGTH_SHORT)
                        .setBackgroundTint(ContextCompat.getColor(this, R.color.red_accent))
                        .setTextColor(ContextCompat.getColor(this, R.color.white))
                        .show()
                    Log.e("ERROR", "Error: API call failed")
                }
            }

        }
        resend.setOnClickListener {
            val jsonObject = JsonObject().apply {
                addProperty("Email", email.toString())
            }

            UserViewModel.sendVerificationEmail(jsonObject) { response, code ->
                if (code == 200) {
                    Log.d("SUCCESS", "Response: $response")
                    Snackbar.make(contextView, "New verification E-mail sent", Snackbar.LENGTH_SHORT)
                        .setBackgroundTint(ContextCompat.getColor(this, R.color.light_green))
                        .setTextColor(ContextCompat.getColor(this, R.color.white))
                        .show()

                } else {
                    Snackbar.make(contextView, "E-mail sending failed", Snackbar.LENGTH_SHORT)
                        .setBackgroundTint(ContextCompat.getColor(this, R.color.red_accent))
                        .setTextColor(ContextCompat.getColor(this, R.color.white))
                        .show()
                    Log.e("ERROR", "Error: API call failed")
                }
            }
        }
>>>>>>> origin/dev
    }
}