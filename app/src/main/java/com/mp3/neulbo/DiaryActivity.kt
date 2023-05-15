package com.mp3.neulbo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class DiaryActivity : AppCompatActivity() {


    private lateinit var goback: ImageButton



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary)


        goback=findViewById(R.id.goBack)


        //뒤로가기 버튼
        goback.setOnClickListener {
            val intent = Intent(this, MainScreen::class.java)
            startActivity(intent)
            finish()
        }





    }
}