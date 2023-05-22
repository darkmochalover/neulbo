package com.mp3.neulbo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton

class DiaryActivity : AppCompatActivity() {


    private lateinit var goback: ImageButton
    private lateinit var save:ImageButton
    private lateinit var edit:EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary)


        goback=findViewById(R.id.goBack)
        save=findViewById(R.id.save)
        edit=findViewById(R.id.diaryEditText)




        //일기내용
        val input = edit.getText().toString()


        //뒤로가기 버튼
        goback.setOnClickListener {
            val intentSend = Intent(this, MainScreen::class.java)
            startActivity(intentSend)
            finish()
        }
        //저장버튼
        save.setOnClickListener{
            val intentSend = Intent(this, MainScreen::class.java)
            intentSend.putExtra("diaryText",input)
            startActivity(intentSend)
            finish()
        }




    }
}