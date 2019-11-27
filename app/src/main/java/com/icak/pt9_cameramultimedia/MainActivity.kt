package com.icak.pt9_cameramultimedia

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.security.Permission

class MainActivity : AppCompatActivity() {
    private var pictureFilePath: String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_capture.setOnClickListener {
            var i = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(i, 123)}

        btn_gallery.setOnClickListener {
            pickImageFromGallery()
        }
    }

    override fun onActivityResult(requestCode: Int,
                                  resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 123)
        {
            var bmp = data?.extras?.get("data") as? Bitmap
            imageView.setImageBitmap(bmp)
        } else if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE){
            imageView.setImageURI(data?.data)
        }
    }


    private fun pickImageFromGallery(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    companion object{
        private val IMAGE_PICK_CODE = 1000
        private val PERMISSION_CODE = 1001
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode){
            PERMISSION_CODE -> {
                if (grantResults.size > 0 && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED){
                        pickImageFromGallery()
                } else {
                    Toast.makeText(this,
                        "Permission denied",
                        Toast.LENGTH_SHORT).show()
                }
            }
        }
    }



}

