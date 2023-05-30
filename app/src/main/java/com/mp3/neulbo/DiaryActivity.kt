package com.mp3.neulbo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

import java.text.SimpleDateFormat
import java.util.*

class DiaryActivity : AppCompatActivity() {

    private lateinit var goback: ImageButton
    private lateinit var save:ImageButton
    private lateinit var edit:EditText
    var auth : FirebaseAuth? = null

    var myRef = FirebaseDatabase.getInstance().reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary)


        goback=findViewById(R.id.goBack)
        save=findViewById(R.id.save)
        edit=findViewById(R.id.diaryEditText)

        auth = Firebase.auth
        //val myUserId = uid
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
            val currentTime = getCurrentDateTime()
            var uid = auth?.currentUser?.uid
            val input = edit!!.text.toString()
            if (uid != null) {
                writeDiary(uid,input, currentTime)
            }

            val intentSend = Intent(this, MainScreen::class.java)
            intentSend.putExtra("diaryText",input)
            startActivity(intentSend)
            finish()
        }
    }

    //현재 날짜와 시간 가져오는 함수
    fun getCurrentDateTime(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val date = Date()
        return dateFormat.format(date)
    }
    private fun writeDiary(userId: String, content: String, Date:String){
        val diary = Diary(content, Date)

        //setValue : 내용 초기화됨 (고쳐야 할듯)
        myRef.child("user").child(userId).push().setValue(diary)
        myRef.child("user").child(userId).child("point").setValue(+150)
    }
}