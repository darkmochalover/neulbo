package com.mp3.neulbo

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.utils.ColorTemplate.COLORFUL_COLORS
import com.mp3.neulbo.databinding.ActivitySignInBinding

class Profile : AppCompatActivity() {

    private lateinit var char: com.github.mikephil.charting.charts.PieChart
    private lateinit var goback: ImageButton

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        char=findViewById(R.id.chart)
        goback=findViewById(R.id.goBack)

        //뒤로가기 버튼
        goback.setOnClickListener {
            val intentSend = Intent(this, MainScreen::class.java)
            startActivity(intentSend)
            finish()
        }

        char.setUsePercentValues(true)



// data Set
        val entries = ArrayList<PieEntry>()
        entries.add(PieEntry(508f, "기쁨"))
        entries.add(PieEntry(600f, "슬픔"))
        entries.add(PieEntry(750f, "당황"))
        entries.add(PieEntry(508f, "화남"))
        entries.add(PieEntry(670f, "즐거움"))
        // add a lot of colors
        val colorsItems = ArrayList<Int>()
        for (c in ColorTemplate.VORDIPLOM_COLORS) colorsItems.add(c)
        for (c in ColorTemplate.JOYFUL_COLORS) colorsItems.add(c)
        for (c in COLORFUL_COLORS) colorsItems.add(c)
        for (c in ColorTemplate.LIBERTY_COLORS) colorsItems.add(c)
        for (c in ColorTemplate.PASTEL_COLORS) colorsItems.add(c)
        colorsItems.add(ColorTemplate.getHoloBlue())

        val pieDataSet = PieDataSet(entries, "")
        pieDataSet.apply {
            colors = colorsItems
            valueTextColor = Color.BLACK
            valueTextSize = 16f
        }
        val pieData = PieData(pieDataSet)
        char.apply {
            data = pieData
            description.isEnabled = false
            isRotationEnabled = false
            setEntryLabelColor(Color.BLACK)
            animateY(1400, Easing.EaseInOutQuad)
            animate()
        }
    }


}