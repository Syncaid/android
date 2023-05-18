package com.example.syncaid.view.patient

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.syncaid.R
import com.example.syncaid.model.User
import com.example.syncaid.view.shared.QRScanner
import com.example.syncaid.viewmodel.userViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ManageGuardians : AppCompatActivity() {


    val UserViewModel = userViewModel()
    var guardianList = ArrayList<User>()

    override fun onResume() {
        super.onResume()
        // Code to execute when the activity resumes
        val recyclerview = findViewById<RecyclerView>(R.id.RecyclerView)
        recyclerview.layoutManager = LinearLayoutManager(this)
        val adapter = guardianAdapter(this,guardianList)
        recyclerview.adapter = adapter


        val sharedPrefs = getSharedPreferences("USER_DATA", Context.MODE_PRIVATE)
        val id = sharedPrefs?.getString("ID","null")


        UserViewModel.getGuardians(id.toString()) { response, code ->
            if (code == 200) {

                guardianList = Gson().fromJson(response.toString(), object : TypeToken<ArrayList<User>>() {}.type) as ArrayList<User>
                Log.d("LIST",guardianList.toString())
                val adapter = guardianAdapter(this,guardianList)
                recyclerview.adapter = adapter


            } else {
                Log.e("ERROR", "Error: API call failed")

            }

        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_guardians)

        val add = findViewById<ImageButton>(R.id.add)

        add.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Link a guardian")
            builder.setMessage("In order to link a guardian to your account scan his QR code")
            builder.setIcon(R.drawable.baseline_qr_code_24)
            builder.setPositiveButton("Start scanning") { dialog, which ->
                // handle Yes button click
                val intent = Intent(this, QRScanner::class.java)
                startActivity(intent)

            }
            builder.setNegativeButton("Cancel") { dialog, which ->
                // handle No button click
            }
            val dialog = builder.create()
            dialog.show()

        }




        val recyclerview = findViewById<RecyclerView>(R.id.RecyclerView)
        recyclerview.layoutManager = LinearLayoutManager(this)
        val adapter = guardianAdapter(this,guardianList)
        recyclerview.adapter = adapter


        val sharedPrefs = getSharedPreferences("USER_DATA", Context.MODE_PRIVATE)
        val id = sharedPrefs?.getString("ID","null")


        UserViewModel.getGuardians(id.toString()) { response, code ->
            if (code == 200) {

                guardianList = Gson().fromJson(response.toString(), object : TypeToken<ArrayList<User>>() {}.type) as ArrayList<User>
                Log.d("LIST",guardianList.toString())
                val adapter = guardianAdapter(this,guardianList)
                recyclerview.adapter = adapter


            } else {
                Log.e("ERROR", "Error: API call failed")

            }

    }
}}