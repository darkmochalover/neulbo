package com.mp3.neulbo

import android.content.Intent
import android.os.Bundle
import android.widget.CalendarView
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class calendar : AppCompatActivity() {
    private lateinit var calendarView: CalendarView
    private lateinit var goback: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.calendar_main)

        calendarView = findViewById(R.id.calendarView)
        goback = findViewById(R.id.goBack)

        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            val calendar = Calendar.getInstance()
            calendar.set(year, month, dayOfMonth)

            val selectedDate = calendar.timeInMillis

            val intent = Intent(this, MemoActivity::class.java)
            intent.putExtra("selectedDate", selectedDate)
            startActivity(intent)
        }
        //뒤로가기 버튼
        goback.setOnClickListener {
            val intent = Intent(this, MainScreen::class.java)
            startActivity(intent)
            finish()
        }
    }
}