package com.example.syncaid.view.shared

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import androidx.core.content.ContextCompat
import com.example.syncaid.R
import com.example.syncaid.viewmodel.userViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.google.gson.JsonObject

class signup2 : AppCompatActivity() {

    val UserViewModel = userViewModel()

    override fun onBackPressed() {
        // do nothing to disable back button
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup2)

        val id = intent.getStringExtra("ID")

        val gender = findViewById<AutoCompleteTextView>(R.id.gender)
        val genderAdapter = ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, arrayOf("Male", "Female"))
        gender.setAdapter(genderAdapter)

        val role = findViewById<AutoCompleteTextView>(R.id.role)
        val roleAdapter = ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, arrayOf("Guardian", "Patient"))
        role.setAdapter(roleAdapter)

        val contextView = findViewById<View>(R.id.view)
        val birthdate = findViewById<EditText>(R.id.birthdate)

        val phone = findViewById<EditText>(R.id.textInputEditText4)
        val next = findViewById<MaterialButton>(R.id.next)

        next.setOnClickListener {
            if (birthdate.text.toString().isEmpty() ||
                role.text.toString().isEmpty() || gender.text.toString().isEmpty() ||
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

                        val jsonObject = JsonObject().apply {
                            addProperty("Role", role.text.toString())
                            addProperty("Tel", phone.text.toString())
                            addProperty("BirthDate", birthdate.text.toString())
                            addProperty("Gender", gender.text.toString())
                        }

                        if (id != null) {
                            UserViewModel.register2(id,jsonObject) { response, code ->
                                if(code == 200) {
                                    Log.d("SUCCESS", "Response: $response")
                                    val intent = Intent(this, alldone::class.java)
                                    intent.putExtra("ID","$id")
                                    startActivity(intent)
                                }
                                else {
                                    Log.e("ERROR", "Error: API call failed")
                                }
                            }
                        }
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


            }

        }




    }
}