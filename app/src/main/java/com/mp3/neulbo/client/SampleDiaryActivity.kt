package com.mp3.neulbo.client

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mp3.neulbo.Diary
import com.mp3.neulbo.MainScreen
import com.mp3.neulbo.R

import android.content.Intent
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.RadioGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import java.text.SimpleDateFormat
import java.util.*

class SampleDiaryActivity : AppCompatActivity() {

    private lateinit var goback: ImageButton
    private lateinit var save:ImageButton
    private lateinit var edit:EditText
    private lateinit var radioGroup: RadioGroup


    lateinit var mRetrofit : Retrofit // 사용할 레트로핏 객체
    lateinit var mRetrofitAPI: RetrofitAPI // 레트로핏 api 객체
    lateinit var mCallAIReply : retrofit2.Call<Emotion> // Json 형식의 데이터를 요청하는 객체
    var input:String=""
    var currentTime:String=""

    var auth : FirebaseAuth? = null
    var myRef = FirebaseDatabase.getInstance().reference
    var param: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample_diary)

        setRetrofit()

        goback=findViewById(R.id.goBack)
        save=findViewById(R.id.save)
        edit=findViewById(R.id.diaryEditText)
        radioGroup = findViewById(R.id.radioGroup)

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
            currentTime = getCurrentDateTime()
            var uid = auth?.currentUser?.uid
            input = edit!!.text.toString()

            val emo = edit.text.toString()
            Log.d("input:" , emo) // input 확인용
            callEmotionDetect(emo)


            val intentSend = Intent(this, MainScreen::class.java)
            intentSend.putExtra("diaryText",input)
            startActivity(intentSend)
            finish()
        }
    }

    //현재 날짜와 시간 가져오는 함수
    fun getCurrentDateTime(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = Date()
        return dateFormat.format(date)
    }
    private fun writeDiary(userId: String, content: String, Date:String, emo:String){
        val isPublic = radioGroup.checkedRadioButtonId == R.id.publicRadio
        val diary = Diary(content, Date, isPublic, emo)

        //setValue : 내용 초기화됨 (고쳐야 할듯)
        myRef.child("user").child(userId).push().setValue(diary)
        myRef.child("user").child(userId).child("point").addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val currentValue = dataSnapshot.getValue(Int::class.java)
                val updatedValue = currentValue?.plus(150)
                myRef.child("user").child(userId).child("point").setValue(updatedValue)
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })


    }

    private fun setRetrofit() {
        // retrofit 으로 가져올 url 을 설정하고 세팅
        mRetrofit = Retrofit.Builder()
            .baseUrl(getString(R.string.baseUrl))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // 인터페이스로 만든 레트로핏 api 요청 받는 것 변수로 등록
        mRetrofitAPI = mRetrofit.create(RetrofitAPI::class.java)
    }

    private val mRetrofitCallback = (object : retrofit2.Callback<Emotion>{
        override fun onResponse(call: Call<Emotion>, response: Response<Emotion>) {
            // 서버에서 데이터 요청 성공시
            var gson = Gson()

//            val result = response.body().toString()
//            Log.d("test", "결과는 ${result}")

            val emotion = response.body()
            val result = gson.toJson(emotion) // Emotion 객체를 JSON 문자열로 변환

            val emotionObj = gson.fromJson(result, Emotion::class.java)
            param = emotionObj.param


            Log.d("test", "결과는 $param")
            val userId = auth?.currentUser?.uid
            if (userId != null) {
                Log.d("test", "들어오긴함")
                changeEmotionValue(userId, param)
                writeDiary(userId,input, currentTime,param)

            }



            // param이 결과 감정.
        }

        override fun onFailure(call: Call<Emotion>, t: Throwable) {
            // 서버 요청 실패
            t.printStackTrace()
            Log.d("test", "에러입니다. ${t.message}")
            ServerConnectErrorToast()
        }
    })


    private fun callEmotionDetect(InputText: String){
//        print("input is: $InputText")

        mCallAIReply = mRetrofitAPI.getAIReply(InputText) // RetrofitAPI 에서 JSON 객체를 요청해서 반환하는 메소드 호출
        mCallAIReply.enqueue(mRetrofitCallback) // 응답을 큐에 넣어 대기 시켜놓음. 즉, 응답이 생기면 뱉어낸다.

    }

    fun changeEmotionValue(userId: String,Emotion:String) {
        val pointRef = myRef.child("user").child(userId).child("emotion").child(Emotion)
        pointRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val currentValue = dataSnapshot.getValue(Int::class.java)
                val updatedValue = currentValue?.plus(1)
                myRef.child("user").child(userId).child("emotion").child(Emotion).setValue(updatedValue)
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
    }
}