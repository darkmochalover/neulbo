package com.mp3.neulbo

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.utils.ColorTemplate.COLORFUL_COLORS
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.mp3.neulbo.databinding.ActivitySignInBinding

class Profile : AppCompatActivity() {

    private lateinit var char: com.github.mikephil.charting.charts.PieChart
    private lateinit var goback: ImageButton
    private lateinit var profile_pic:ImageView
    var auth : FirebaseAuth? = null

    var myRef = FirebaseDatabase.getInstance().reference

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        auth = Firebase.auth
        val uid = auth?.currentUser?.uid

        char=findViewById(R.id.chart)
        goback=findViewById(R.id.goBack)
        profile_pic=findViewById(R.id.profile_pic)

        //뒤로가기 버튼
        goback.setOnClickListener {
            val intentSend = Intent(this, MainScreen::class.java)
            startActivity(intentSend)
            finish()
        }
        var ang = 0
        var fear = 0
        var sad = 0
        var ntr = 0
        var hpy = 0
        var hte = 0
        var spr = 0


        // Counter to keep track of the number of completed callbacks
        var callbackCount = 0

        // Define a function to handle the completion of all callbacks
        fun handleCallbackCompletion() {
            // Increment the callback count
            callbackCount++

            // Check if all callbacks have completed
            if (callbackCount == 7) {
                // All callbacks have completed, execute the code here
                // data Set
                val entries = ArrayList<PieEntry>()
                if(fear!=0) {
                    entries.add(PieEntry(fear.toFloat(), "공포"))
                }
                if(ang!=0) {
                    entries.add(PieEntry(ang.toFloat(), "분노"))
                }
                if(sad!=0) {
                    entries.add(PieEntry(sad.toFloat(), "슬픔"))
                }
                if(ntr!=0) {
                    entries.add(PieEntry(ntr.toFloat(), "중립"))
                }
                if(hpy!=0) {
                    entries.add(PieEntry(hpy.toFloat(), "행복"))
                }
                if(hte!=0) {
                    entries.add(PieEntry(hte.toFloat(), "혐오"))
                }
                if(spr!=0) {
                    entries.add(PieEntry(spr.toFloat(), "놀람"))
                }
                char.setUsePercentValues(true)


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

        getEmotionValue(uid.toString(), "분노") { currentValue ->
            if (currentValue != null) {
                ang = currentValue.toInt()
            }
            handleCallbackCompletion()
        }

        getEmotionValue(uid.toString(), "공포") { currentValue ->
            if (currentValue != null) {
                fear = currentValue.toInt()
            }
            handleCallbackCompletion()
        }

        getEmotionValue(uid.toString(), "슬픔") { currentValue ->
            if (currentValue != null) {
                sad = currentValue.toInt()
            }
            handleCallbackCompletion()
        }

        getEmotionValue(uid.toString(), "중립") { currentValue ->
            if (currentValue != null) {
                ntr = currentValue.toInt()
            }
            handleCallbackCompletion()
        }

        getEmotionValue(uid.toString(), "행복") { currentValue ->
            if (currentValue != null) {
                hpy = currentValue.toInt()
            }
            handleCallbackCompletion()
        }

        getEmotionValue(uid.toString(), "혐오") { currentValue ->
            if (currentValue != null) {
                hte = currentValue.toInt()
            }
            handleCallbackCompletion()
        }

        getEmotionValue(uid.toString(), "놀람") { currentValue ->
            if (currentValue != null) {
                spr = currentValue.toInt()
            }
            handleCallbackCompletion()
        }

    }
    fun getEmotionValue(userId: String,Emotion:String ,callback: (Int?) -> Unit) {
        val pointRef = myRef.child("user").child(userId).child("emotion").child(Emotion)
        pointRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val currentValue = dataSnapshot.getValue(Int::class.java)?.toInt()
                callback(currentValue)
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
    }

}