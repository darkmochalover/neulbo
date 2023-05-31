package com.mp3.neulbo.stampBox

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Button
import android.widget.ImageView
import com.mp3.neulbo.StampBoxActivity

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.provider.MediaStore

import com.mp3.neulbo.*
import java.io.ByteArrayOutputStream

class AddStampActivity : AppCompatActivity() {

    private lateinit var goback: ImageButton

    private lateinit var addImage: Button
    private lateinit var addImageView: ImageView

    private lateinit var addSaveImage: ImageButton
    private lateinit var addImageText: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stamp_add)

        addImage = findViewById(R.id.addImage)
        addImageView = findViewById(R.id.addImageView)


        addImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, PICK_IMAGE_REQUEST)
        }



        addSaveImage = findViewById(R.id.addSaveImage)
        addImageText = findViewById(R.id.addImageText)
        addSaveImage.setOnClickListener {
            val text = addImageText.text.toString()
            val bitmap = (addImageView.drawable as BitmapDrawable).bitmap

            val MAX_IMAGE_SIZE = 1024 // 원하는 이미지 최대 크기 (픽셀 단위)

// 이미지 크기 조정
            val scaledBitmap = scaleBitmap(bitmap, MAX_IMAGE_SIZE)

// 이미지 압축
            val compressedBitmap = compressBitmap(scaledBitmap, 80) // 품질을 조정하여 압축


            val intentSend = Intent(this, StampBoxActivity::class.java)
            intentSend.putExtra("imageBitmap", compressedBitmap)
            intentSend.putExtra("textData", text)
            startActivity(intentSend)
            finish()
        }


        //뒤로가기 버튼
        goback=findViewById(R.id.goBack)
        goback.setOnClickListener {
            val intentSend = Intent(this, StampBoxActivity::class.java)
            startActivity(intentSend)
            finish()
        }

    }

    companion object {
        private const val PICK_IMAGE_REQUEST = 1
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImage = data.data
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedImage)
            addImageView.setImageBitmap(bitmap)
        }
    }

    // 비트맵 크기 조정
    private fun scaleBitmap(bitmap: Bitmap, maxSize: Int): Bitmap {
        val width = bitmap.width
        val height = bitmap.height

        val scaleFactor = minOf(maxSize.toFloat() / width, maxSize.toFloat() / height)
        val scaledWidth = (width * scaleFactor).toInt()
        val scaledHeight = (height * scaleFactor).toInt()

        return Bitmap.createScaledBitmap(bitmap, scaledWidth, scaledHeight, true)
    }

    // 비트맵 압축
    private fun compressBitmap(bitmap: Bitmap, quality: Int): ByteArray {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, stream)
        return stream.toByteArray()
    }

}