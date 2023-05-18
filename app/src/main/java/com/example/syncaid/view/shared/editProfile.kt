package com.example.syncaid.view.shared

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.bumptech.glide.request.RequestOptions
import com.example.syncaid.R
import com.example.syncaid.viewmodel.userViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.google.gson.JsonObject
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.security.MessageDigest


class editProfile : AppCompatActivity() {

    val PICK_IMAGE_REQUEST = 1
    var selectedImage: Uri? = null
    lateinit var image:ImageView
    val UserViewModel = userViewModel()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        image = findViewById(R.id.imageview)
        val contextView = findViewById<View>(R.id.view)
        val firstname = findViewById<EditText>(R.id.firstname)
        val lastname = findViewById<EditText>(R.id.lastname)
        val email = findViewById<EditText>(R.id.email)
        val save = findViewById<MaterialButton>(R.id.edit)
        val editphoto = findViewById<TextView>(R.id.editbtn)
        val sharedPref = getSharedPreferences("USER_DATA", Context.MODE_PRIVATE)
        val id = sharedPref.getString("ID",null)
        val options = RequestOptions()
            .override(200, 200)

        val photo = sharedPref.getString("PROFILEPHOTO",null).toString()
      Glide.with(this)
            .load(Uri.parse(photo))
            .circleCrop()
          .diskCacheStrategy(DiskCacheStrategy.NONE)
          .skipMemoryCache(true)
            .apply(options)
            .into(image)


        Log.d("PROFILEPHOTO",sharedPref.getString("PROFILEPHOTO",null).toString())
        firstname.setText(sharedPref.getString("FIRSTNAME",null).toString())
        lastname.setText(sharedPref.getString("LASTNAME",null).toString())
        email.setText(sharedPref.getString("EMAIL",null).toString())


        save.setOnClickListener {

            if (firstname.text.toString().isEmpty() ||
                lastname.text.toString().isEmpty() ||
                email.text.toString().isEmpty()
            ) {
                Snackbar.make(contextView, "All fields are required", Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(ContextCompat.getColor(this, R.color.red_accent))
                    .setTextColor(ContextCompat.getColor(this, R.color.white))
                    .show()
            }
            else {
                val emailLowerCase = email.text.toString().trim().lowercase()
                if (android.util.Patterns.EMAIL_ADDRESS.matcher(emailLowerCase).matches() ) {

                    val jsonObject = JsonObject().apply {
                        addProperty("Email", email.text.toString())
                        addProperty("FirstName", firstname.text.toString())
                        addProperty("LastName", lastname.text.toString())
                    }

                    if (id != null) {
                        UserViewModel.update(id,jsonObject) { response, code ->

                            if (code == 200) {

                                Log.d("SUCCESS", "Response: $response")

                                val jsonResponse = JSONObject(response.toString())
                                val editor = sharedPref.edit()
                                editor.putString("EMAIL",email.text.toString())
                                editor.putString("FIRSTNAME",firstname.text.toString())
                                editor.putString("LASTNAME",lastname.text.toString())
                                editor.apply()

                                Snackbar.make(contextView, "Changes saved", Snackbar.LENGTH_SHORT)
                                    .setBackgroundTint(ContextCompat.getColor(this, R.color.light_green))
                                    .setTextColor(ContextCompat.getColor(this, R.color.white))
                                    .show()



                                val handler = Handler(Looper.getMainLooper())
                                val myTask = Runnable {
                                    finish()
                                }
                                handler.postDelayed(myTask, 1000)

                            } else {
                                Snackbar.make(contextView, "Saving changes failed", Snackbar.LENGTH_SHORT)
                                    .setBackgroundTint(ContextCompat.getColor(this, R.color.red_accent))
                                    .setTextColor(ContextCompat.getColor(this, R.color.white))
                                    .show()
                                Log.e("ERROR", "Error: API call failed")
                            }
                        }
                    }

                }
            }

        }

        editphoto.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
        }




    }





    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {




        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            val uri: Uri = data.data!!

            selectedImage = uri
            try {
                val options = RequestOptions()
                    .override(200, 200)
                val inputStream = contentResolver.openInputStream(uri)
                val selectedImage = BitmapFactory.decodeStream(inputStream)
                Glide.with(this)
                    .load(uri)
                    .apply(options)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .circleCrop()
                    .into(image)


                val sharedPref = getSharedPreferences("USER_DATA", Context.MODE_PRIVATE)
                val id = sharedPref.getString("ID",null)

                val file = File(this.cacheDir, id+".jpg")
                file.createNewFile()

                val bos = ByteArrayOutputStream()
                selectedImage.compress(Bitmap.CompressFormat.JPEG, 100, bos)
                val bitmapData = bos.toByteArray()


                val fos = FileOutputStream(file)
                fos.write(bitmapData)
                fos.flush()
                fos.close()
                val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
                val filePart = MultipartBody.Part.createFormData("ProfilePhoto", file.name, requestFile)

                if (id != null) {

                    UserViewModel.updatePhoto(id,filePart) { response, code ->

                        if (code == 200) {
                            Log.d("SUCCESS", "Response: $response")
                            val editor = sharedPref.edit()
                            val jsonResponse = JSONObject(response)
                            val url = jsonResponse.getString("URL")
                            editor.putString("PROFILEPHOTO",url)
                            editor.apply()

                        } else {
                            Toast.makeText(this, "Photo upload error", Toast.LENGTH_LONG).show()
                            Log.e("ERROR", "Error: API call failed")
                        }
                    }
                }


            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}

