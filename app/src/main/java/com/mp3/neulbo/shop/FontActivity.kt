package com.mp3.neulbo.shop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import com.mp3.neulbo.ShopActivity


import com.mp3.neulbo.*

class FontActivity : AppCompatActivity() {

    private lateinit var goback: ImageButton

    private lateinit var font1: ImageButton
    private lateinit var font2: ImageButton
    private lateinit var font3: ImageButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_font)


        goback=findViewById(R.id.goBack)

        font1 = findViewById(R.id.font1Image)
        font2 = findViewById(R.id.font2Image)
        font3 = findViewById(R.id.font3Image)


        //뒤로가기 버튼
        goback.setOnClickListener {
            val intentSend = Intent(this, ShopActivity::class.java)
            startActivity(intentSend)
            finish()
        }


    }
}