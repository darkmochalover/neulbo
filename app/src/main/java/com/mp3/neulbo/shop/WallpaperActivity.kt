package com.mp3.neulbo.shop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import com.mp3.neulbo.ShopActivity


import com.mp3.neulbo.*

class WallpaperActivity : AppCompatActivity() {

    private lateinit var goback: ImageButton

    private lateinit var wallpaper1: ImageButton
    private lateinit var wallpaper2: ImageButton
    private lateinit var wallpaper3: ImageButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_wallpaper)


        goback=findViewById(R.id.goBack)

        wallpaper1 = findViewById(R.id.wallpaper1Image)
        wallpaper2 = findViewById(R.id.wallpaper2Image)
        wallpaper3 = findViewById(R.id.wallpaper3Image)


        //뒤로가기 버튼
        goback.setOnClickListener {
            val intentSend = Intent(this, ShopActivity::class.java)
            startActivity(intentSend)
            finish()
        }


    }
}