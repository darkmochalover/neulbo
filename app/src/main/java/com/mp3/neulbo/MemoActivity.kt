package com.mp3.neulbo

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import java.text.SimpleDateFormat
import java.util.*
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

class MemoActivity : AppCompatActivity() {
    private lateinit var dateTextView: TextView
    private lateinit var diaryEditText: EditText
    private lateinit var replyEditText: EditText
    private lateinit var checkButton: ImageButton

    var auth : FirebaseAuth? = null
    var myRef = FirebaseDatabase.getInstance().reference.child("user")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memo)

        dateTextView = findViewById(R.id.dateTextView)
        diaryEditText = findViewById(R.id.diary)
        replyEditText = findViewById(R.id.reply)
        checkButton = findViewById(R.id.CheckButton)

        // 선택된 날짜 정보 가져오기
        var selectedDate = intent.getLongExtra("selectedDate", 0)
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = selectedDate
        val dateFormat = SimpleDateFormat("yyyy년 MM월 dd일", Locale.getDefault())
        val dateFormat2 = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val dateStr = dateFormat.format(calendar.time)

        dateTextView.text = dateStr
        //사용자 ID와 선택한 날짜 설정
        auth = Firebase.auth
        var uid = auth?.currentUser?.uid.toString()

        fetchUserDiaries(uid, dateFormat2.format(calendar.time), diaryEditText, replyEditText)

        checkButton.setOnClickListener {
            finish()
        }
    }
    //사용자 ID에 해당하는 모든 일기 불러오기
    fun fetchUserDiaries(userId: String, selectedDate: String, diaryEditText: EditText, replyEditText: EditText) {
        val query = myRef.child(userId).orderByChild("date").equalTo(selectedDate)

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (diarySnapshot in snapshot.children){
                        val content = diarySnapshot.child("content").getValue(String::class.java)
                        //val commentSnapshot = diarySnapshot.child("commentId")

                        //EditText에 일기 내용 설정
                        diaryEditText.setText(content)

                        fetchCommentFromDiary(diarySnapshot, replyEditText)
                        }
                }
                else {
                    //해당 날짜 일기 없는 경우
                }
            }
            override fun onCancelled(error: DatabaseError){
                //데이터 읽기 취소될 시 호출됨
                //오류 처리 수행 가능
            }
        })
    }
    private fun fetchCommentFromDiary(diarySnapshot: DataSnapshot, replyEditText: EditText) {
        for (commentIdSnapshot in diarySnapshot.children) {
            if (commentIdSnapshot.key != "content" && commentIdSnapshot.key != "date"){
                val commentContent = commentIdSnapshot.child("comment").getValue(String::class.java)
                replyEditText.setText(commentContent)
                break  // 첫 번째 댓글만 가져오고 종료
            }
        }
    }
}