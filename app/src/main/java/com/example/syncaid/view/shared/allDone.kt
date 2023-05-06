package com.example.syncaid.view.shared

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.syncaid.MainActivity
import com.example.syncaid.R
import com.google.android.material.button.MaterialButton

class allDone : AppCompatActivity() {

    override fun onBackPressed() {
        // do nothing to disable back button
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alldone)


        val loginbtn = findViewById<MaterialButton>(R.id.next)

        loginbtn.setOnClickListener{
            val intent = Intent(this,MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }

    }
}