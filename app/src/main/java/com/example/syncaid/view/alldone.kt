package com.example.syncaid.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.syncaid.R
import com.google.android.material.button.MaterialButton

class alldone : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alldone)

        val doneBtn = findViewById<MaterialButton>(R.id.next)

        doneBtn.setOnClickListener {
            startActivity(Intent(this,login::class.java))
        }
    }




}