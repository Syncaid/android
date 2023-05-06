package com.example.syncaid

<<<<<<< HEAD
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.syncaid.view.shared.login
import com.example.syncaid.view.shared.signup
=======
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.example.syncaid.view.patient.patientNav
import com.example.syncaid.view.shared.login
import com.example.syncaid.view.shared.signup
import com.example.syncaid.view.shared.verifyEmail
>>>>>>> origin/dev
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
<<<<<<< HEAD
        setContentView(R.layout.activity_main)

=======











        setContentView(R.layout.activity_main)

        val sharedPref = getSharedPreferences("USER_DATA", Context.MODE_PRIVATE)
        val verified = sharedPref.getBoolean("VERIFIED",false)
        val role = sharedPref.getString("ROLE","null")
        val loggedin = sharedPref.getBoolean("LOGGEDIN",false)
        val email = sharedPref.getString("EMAIL","null")
        val id = sharedPref.getString("ID","null")




        if (loggedin) {
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
                val intent = Intent(this, verifyEmail::class.java)
                intent.putExtra("EMAIL",email.toString())
                intent.putExtra("ID",id.toString())
                startActivity(intent)
            }


        }



>>>>>>> origin/dev
        if (getSupportActionBar() != null) {
            getSupportActionBar()?.hide();
        }

        val loginbtn = findViewById<MaterialButton>(R.id.alreadyamember)
        val signupbtn = findViewById<MaterialButton>(R.id.becomeamember)
        loginbtn.setOnClickListener {

            startActivity(Intent(this, login::class.java))
        }

        signupbtn.setOnClickListener {
            startActivity(Intent(this, signup::class.java))
        }
    }
}