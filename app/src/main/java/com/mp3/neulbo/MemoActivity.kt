package com.mp3.neulbo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class MemoActivity : AppCompatActivity() {
    private lateinit var dateTextView: TextView
    private lateinit var diaryEditText: EditText
    private lateinit var replyEditText: EditText
    private lateinit var checkButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memo)

        dateTextView = findViewById(R.id.dateTextView)
        diaryEditText = findViewById(R.id.diary)
        replyEditText = findViewById(R.id.reply)
        checkButton = findViewById(R.id.CheckButton)

        // 선택된 날짜 정보 가져오기
        val selectedDate = intent.getLongExtra("selectedDate", 0)
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = selectedDate
        val dateFormat = SimpleDateFormat("yyyy년 MM월 dd일", Locale.getDefault())
        val dateStr = dateFormat.format(calendar.time)

        dateTextView.text = dateStr

        checkButton.setOnClickListener {
            val diary = diaryEditText.text.toString()
            val reply = replyEditText.text.toString()

            saveDiary(selectedDate, diary, reply)
            finish()
        }
    }

    private fun saveDiary(date: Long, diary: String, reply: String) {
        // 여기에 일기랑 댓글 내용 저장하는 로직을 구현합니다.
        // 실제로 데이터를 저장하거나 처리해야 하는 경우 여기에 작성합니다.
        // 이 예시에서는 데이터를 저장하지 않고 Activity를 닫는 동작만 수행합니다.
    }
}