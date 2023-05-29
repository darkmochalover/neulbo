package com.mp3.neulbo

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
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
    private lateinit var profile_pic:ImageView

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        char=findViewById(R.id.chart)
        goback=findViewById(R.id.goBack)
        profile_pic=findViewById(R.id.profile_pic)

        //뒤로가기 버튼
        goback.setOnClickListener {
            val intentSend = Intent(this, MainScreen::class.java)
            startActivity(intentSend)
            finish()
        }

        char.setUsePercentValues(true)



// data Set
        val entries = ArrayList<PieEntry>()
        entries.add(PieEntry(508f, "공포"))
        entries.add(PieEntry(600f, "분노"))
        entries.add(PieEntry(750f, "슬픔"))
        entries.add(PieEntry(508f, "중립"))
        entries.add(PieEntry(623f, "행복"))
        entries.add(PieEntry(111f, "혐오"))
        entries.add(PieEntry(243f, "놀람"))

        var maxValue = Float.MIN_VALUE
        var maxLabel=""
        for (entry in entries) {
            if (entry.value > maxValue) {
                maxValue = entry.value
                maxLabel = entry.label
            }
        }
        when(maxLabel){
            in "공포"->profile_pic.setImageResource(R.drawable.profile_fear_edit)
            in "분노"->profile_pic.setImageResource(R.drawable.profile_angry_edit)
            in "슬픔"->profile_pic.setImageResource(R.drawable.profile_sad_edit)
            in "중립"->profile_pic.setImageResource(R.drawable.profile_basic_edit)
            in "행복"->profile_pic.setImageResource(R.drawable.profile_happy_edit)
            in "혐오"->profile_pic.setImageResource(R.drawable.profile_hate_edit)
            in "놀람"->profile_pic.setImageResource(R.drawable.profile_surprised_edit)
        }
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