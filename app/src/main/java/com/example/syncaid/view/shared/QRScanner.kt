package com.example.syncaid.view.shared

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.syncaid.R
import com.example.syncaid.view.patient.captureOverride
import com.example.syncaid.viewmodel.userViewModel
import com.google.gson.JsonObject
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult

class QRScanner : AppCompatActivity() {

    val UserViewModel = userViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)




        // Request camera permission if not granted
        if (!hasCameraPermission()) {
            requestCameraPermission()
        } else {
            // Launch barcode scanner
            launchBarcodeScanner()
        }
    }

    // Check if camera permission is granted
    private fun hasCameraPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            "android.permission.CAMERA"
        ) == PackageManager.PERMISSION_GRANTED
    }

    // Request camera permission
    private fun requestCameraPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf("android.permission.CAMERA"),
            CAMERA_PERMISSION_REQUEST_CODE
        )
    }

    // Handle camera permission request result
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            CAMERA_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    launchBarcodeScanner()
                } else {
                    Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    // Launch barcode scanner
    private fun launchBarcodeScanner() {



        IntentIntegrator(this).apply {
            setPrompt("Place the QR in the box") // Disable the default prompt message
            setOrientationLocked(false)
            setCameraId(0)
            setCaptureActivity(captureOverride::class.java)
            setBeepEnabled(false)
            initiateScan()
        }
    }

    // Handle barcode scanner result
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val contextView = findViewById<View>(R.id.view)
        val result: IntentResult? = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents != null) {

                val sharedPrefs = getSharedPreferences("USER_DATA", Context.MODE_PRIVATE)
                val id = sharedPrefs?.getString("ID","null")

                val jsonObject = JsonObject().apply {
                    addProperty("Email", result.contents.toString())
                }


                UserViewModel.addGuardian(id.toString(),jsonObject) { response, code ->
                    Log.d("RESPONSE",response.toString())
                    if (code == 200) {


                        Toast.makeText(this, "Linked successfully", Toast.LENGTH_LONG).show()

                          finish()




                    } else {
                        Log.e("ERROR", "Error: API call failed")
                        Toast.makeText(this, "Invalid QR code", Toast.LENGTH_LONG).show()
                            finish()

                    }

                }
            } else {
                    finish()

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    companion object {
        private const val CAMERA_PERMISSION_REQUEST_CODE = 100
    }
}