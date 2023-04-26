package com.example.syncaid.view.shared

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.syncaid.R
import com.google.android.material.button.MaterialButton

class recoverPassword : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recoverpassword)

        val email = findViewById<TextView>(R.id.email)
        val recoverBtn = findViewById<MaterialButton>(R.id.recoverbtn)
    }
}