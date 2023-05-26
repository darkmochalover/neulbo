package com.mp3.neulbo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import com.google.firebase.database.FirebaseDatabase

class DiaryActivity : AppCompatActivity() {


    private lateinit var goback: ImageButton
    private lateinit var save:ImageButton
    private lateinit var edit:EditText
    var myRef = FirebaseDatabase.getInstance().reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary)


        goback=findViewById(R.id.goBack)
        save=findViewById(R.id.save)
        edit=findViewById(R.id.diaryEditText)

        //val user = Firebase.auth.currentUser




        //뒤로가기 버튼
        goback.setOnClickListener {
            val intentSend = Intent(this, MainScreen::class.java)
            startActivity(intentSend)
            finish()
        }
        //저장버튼
        save!!.setOnClickListener{
            //일기내용
            val input = edit!!.text.toString()
            writeDiary("user",input)

            val intentSend = Intent(this, MainScreen::class.java)
            intentSend.putExtra("diaryText",input)
            startActivity(intentSend)
            finish()
        }




    }
    private fun writeDiary(userId: String, content: String){
        val diary = Diary(content)
        myRef.push().child(userId.toString()).setValue(diary)
    }
}