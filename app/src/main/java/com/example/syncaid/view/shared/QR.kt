package com.example.syncaid.view.shared

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import com.example.syncaid.R
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import java.util.Hashtable

class QR : AppCompatActivity() {

    fun generateQRCodeImage(text: String, width: Int = 225, height: Int = 225): Bitmap {
        val hints = Hashtable<EncodeHintType, Any>()
        hints[EncodeHintType.ERROR_CORRECTION] = ErrorCorrectionLevel.H
        val writer = QRCodeWriter()
        val bitMatrix = writer.encode(text, BarcodeFormat.QR_CODE, width, height, hints)
        val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        for (x in 0 until width) {
            for (y in 0 until height) {
                bmp.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
            }
        }
        return bmp
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr)

        val sharedPref = getSharedPreferences("USER_DATA", Context.MODE_PRIVATE)
        val email = sharedPref.getString("EMAIL",null)


        val button = findViewById<Button>(R.id.button)
        val qrImage = findViewById<ImageView>(R.id.qr)
        val qrCodeImage = generateQRCodeImage(email.toString())
        qrImage.setImageBitmap(qrCodeImage)

        button.setOnClickListener {
            finish()
        }


    }
}