package com.example.syncaid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.syncaid.view.login
import com.example.syncaid.view.signup
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (getSupportActionBar() != null) {
            getSupportActionBar()?.hide();
        }

        val loginbtn = findViewById<MaterialButton>(R.id.alreadyamember)
        val signupbtn = findViewById<MaterialButton>(R.id.becomeamember)
        loginbtn.setOnClickListener {

            startActivity(Intent(this,login::class.java))
        }

        signupbtn.setOnClickListener {
            startActivity(Intent(this,signup::class.java))
        }
    }
}