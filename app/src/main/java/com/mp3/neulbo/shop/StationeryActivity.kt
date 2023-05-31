package com.mp3.neulbo.shop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import com.mp3.neulbo.ShopActivity


import com.mp3.neulbo.*

class StationeryActivity : AppCompatActivity() {

    private lateinit var goback: ImageButton

    private lateinit var stationery1: ImageButton
    private lateinit var stationery2: ImageButton
    private lateinit var stationery3: ImageButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_stationery)


        goback=findViewById(R.id.goBack)

        stationery1 = findViewById(R.id.stationery1Image)
        stationery2 = findViewById(R.id.stationery2Image)
        stationery3 = findViewById(R.id.stationery3Image)


        //뒤로가기 버튼
        goback.setOnClickListener {
            val intentSend = Intent(this, ShopActivity::class.java)
            startActivity(intentSend)
            finish()
        }


    }
}