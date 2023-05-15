package com.mp3.neulbo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class MainScreen : AppCompatActivity() {


    private lateinit var add_button: ImageButton
    private lateinit var calendar_button: ImageButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen)

        add_button = findViewById(R.id.add)
        calendar_button=findViewById(R.id.calendar)

        //일기 추가 버튼
        add_button.setOnClickListener {
            val intent = Intent(this, DiaryActivity::class.java)
            startActivity(intent)
            finish()

        }
        calendar_button.setOnClickListener{
            val intent = Intent(this, calendar::class.java)
            startActivity(intent)
            finish()
        }


    }


}