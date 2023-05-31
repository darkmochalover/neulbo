package com.mp3.neulbo.shop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import com.mp3.neulbo.ShopActivity


import com.mp3.neulbo.*

class OrgamiActivity : AppCompatActivity() {

    private lateinit var goback: ImageButton

    private lateinit var orgami1: ImageButton
    private lateinit var orgami2: ImageButton
    private lateinit var orgami3: ImageButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_orgami)


        goback=findViewById(R.id.goBack)

        orgami1 = findViewById(R.id.orgami1Image)
        orgami2 = findViewById(R.id.orgami2Image)
        orgami3 = findViewById(R.id.orgami3Image)


        //뒤로가기 버튼
        goback.setOnClickListener {
            val intentSend = Intent(this, ShopActivity::class.java)
            startActivity(intentSend)
            finish()
        }


    }
}