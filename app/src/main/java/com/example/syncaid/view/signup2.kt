package com.example.syncaid.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import androidx.core.content.ContextCompat
import com.example.syncaid.R
import com.example.syncaid.viewmodel.verificationpage
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.MaterialAutoCompleteTextView

class signup2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup2)
        val gender = findViewById<AutoCompleteTextView>(R.id.gender)
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, arrayOf("Male", "Female"))
        gender.setAdapter(adapter)

        val contextView = findViewById<View>(R.id.view)
        val birthdate = findViewById<EditText>(R.id.birthdate)
        val country = findViewById<EditText>(R.id.textInputEditText3)
        val phone = findViewById<EditText>(R.id.textInputEditText4)
        val next = findViewById<MaterialButton>(R.id.next)

        next.setOnClickListener {
            if (birthdate.text.toString().isEmpty() ||
                country.text.toString().isEmpty() ||
                phone.text.toString().isEmpty()
            ) {
                Snackbar.make(contextView, "All fields are required", Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(ContextCompat.getColor(this, R.color.red_accent))
                    .setTextColor(ContextCompat.getColor(this, R.color.white))
                    .show()
            }
            else {
                val parts = birthdate.text.toString().trim().split("/")
                val day = parts[0].toInt()
                val month = parts[1].toInt()
                val year = parts[2].toInt()
                val datepattern = "\\d{2}/\\d{2}/\\d{4}"
                if (Regex(datepattern).matches(birthdate.text.toString()) && day in 1..31 && month in 1..12 && year >= 1900 && year <= 2999) {

                    val phonepattern = "\\d{8}"
                    if(Regex(phonepattern).matches(phone.text.toString())) {
                        // signup logic
                        startActivity(Intent(this,alldone::class.java))
                    }
                    else
                    {
                        Snackbar.make(contextView, "Phone number should be 8 digits long", Snackbar.LENGTH_SHORT)
                            .setBackgroundTint(ContextCompat.getColor(this, R.color.red_accent))
                            .setTextColor(ContextCompat.getColor(this, R.color.white))
                            .show()
                    }
                }
                else {
                    Snackbar.make(contextView, "Invalid birthdate, should be in this format dd/mm/yy", Snackbar.LENGTH_SHORT)
                        .setBackgroundTint(ContextCompat.getColor(this, R.color.red_accent))
                        .setTextColor(ContextCompat.getColor(this, R.color.white))
                        .show()

                }

                startActivity(Intent(this,verificationpage::class.java))
            }

        }




    }
}