package com.example.syncaid.view.shared

<<<<<<< HEAD
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.syncaid.R

class recoverPassword2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recoverpassword2)

=======
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.syncaid.MainActivity
import com.example.syncaid.R
import com.example.syncaid.viewmodel.userViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.google.gson.JsonObject
import org.json.JSONObject
import org.w3c.dom.Text

class recoverPassword2 : AppCompatActivity() {

    val UserViewModel = userViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recoverpassword2)

        val trueOTP = intent.getStringExtra("OTP")
        val email = intent.getStringExtra("EMAIL")

        val contextView = findViewById<View>(R.id.view)
        val otp = findViewById<EditText>(R.id.otpfield)
        val pw = findViewById<EditText>(R.id.passwordfield)
        val pw2 = findViewById<EditText>(R.id.confirmfield)
        val pwText = findViewById<TextView>(R.id.newpassword)
        val pw2Text = findViewById<TextView>(R.id.confirmpass)

        val button = findViewById<MaterialButton>(R.id.reset)



        button.setOnClickListener {
            if ( otp.text.toString().isEmpty() ||  pw.text.toString().isEmpty() || pw2.text.toString().isEmpty()){
                Snackbar.make(contextView, "All fields must be filled", Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(ContextCompat.getColor(this, R.color.red_accent))
                    .setTextColor(ContextCompat.getColor(this, R.color.white))
                    .show()
            }
            else {
                if(pw.text.toString().length < 8 || pw2.text.toString().length < 8) {
                    Snackbar.make(contextView, "Passwords must be at least 8 characters long", Snackbar.LENGTH_SHORT)
                        .setBackgroundTint(ContextCompat.getColor(this, R.color.red_accent))
                        .setTextColor(ContextCompat.getColor(this, R.color.white))
                        .show()
                } else {
                    if(pw.text.toString().equals(pw2.text.toString())) {

                        val jsonObject = JsonObject().apply {
                            addProperty("Password", pw.text.toString())
                            addProperty("Email", email.toString())
                            addProperty("vString", otp.text.toString())
                        }

                        UserViewModel.recoverPassword2(jsonObject) { response, code ->
                            Log.d("res",response.toString())
                            if (code == 200) {
                                Log.d("SUCCESS", "Response: $response")


                                val intent = Intent(this, login::class.java)
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
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
                        Snackbar.make(contextView, "Passwords do not match", Snackbar.LENGTH_SHORT)
                            .setBackgroundTint(ContextCompat.getColor(this, R.color.red_accent))
                            .setTextColor(ContextCompat.getColor(this, R.color.white))
                            .show()
                    }

                }



            }
        }






>>>>>>> origin/dev
    }
}