package com.mp3.neulbo

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast

import com.mp3.neulbo.stampBox.*

class StampBoxActivity : AppCompatActivity() {

    private lateinit var goback: ImageButton
    private lateinit var addStamp: ImageButton

    private lateinit var button_cloud1: ImageButton
    private lateinit var button_cloud2: ImageButton
    private lateinit var button_cloud3: ImageButton


    private lateinit var button_imported: ImageButton
    private lateinit var text_imported: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stamp_box)


        goback=findViewById(R.id.goBack)
        addStamp = findViewById(R.id.addStamp)

        // cloud list
        button_cloud1 = findViewById(R.id.cloud1Image)
        button_cloud2 = findViewById(R.id.cloud2Image)
        button_cloud3 = findViewById(R.id.cloud3Image)


        button_imported = findViewById(R.id.importedImage)
        text_imported = findViewById(R.id.importedText)
        val receivedIntent = intent
        val text = receivedIntent.getStringExtra("textData")
        val compressedBitmap = receivedIntent.getByteArrayExtra("imageBitmap")

        if (text != null) {
            text_imported.text = text
        }

        if (compressedBitmap != null) {
            val bitmap = BitmapFactory.decodeByteArray(compressedBitmap, 0, compressedBitmap.size)
            button_imported.setImageBitmap(bitmap)
        }


        addStamp.setOnClickListener {
            val intentSend = Intent(this, AddStampActivity::class.java)
            startActivity(intentSend)
            finish()
        }



        //뒤로가기 버튼
        goback.setOnClickListener {
            val intentSend = Intent(this, MainScreen::class.java)
            startActivity(intentSend)
            finish()
        }


    }
}