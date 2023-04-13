package com.example.syncaid.view

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.syncaid.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView

class recoverpassword : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recoverpassword)

        val email = findViewById<TextView>(R.id.email)
        val recoverBtn = findViewById<MaterialButton>(R.id.recoverbtn)
    }
}