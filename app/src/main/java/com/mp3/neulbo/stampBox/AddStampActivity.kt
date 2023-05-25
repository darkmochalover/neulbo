package com.mp3.neulbo.stampBox

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import com.mp3.neulbo.StampBoxActivity


import com.mp3.neulbo.*

class AddStampActivity : AppCompatActivity() {

    private lateinit var goback: ImageButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stamp_add)


        goback=findViewById(R.id.goBack)


        //뒤로가기 버튼
        goback.setOnClickListener {
            val intentSend = Intent(this, StampBoxActivity::class.java)
            startActivity(intentSend)
            finish()
        }


    }
}