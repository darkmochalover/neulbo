package com.mp3.neulbo.client

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.gson.Gson
import com.mp3.neulbo.R
import com.mp3.neulbo.databinding.ActivityEmotionBinding
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class EmotionActivity : AppCompatActivity() {
    lateinit var binding : ActivityEmotionBinding
    lateinit var mRetrofit : Retrofit // 사용할 레트로핏 객체
    lateinit var mRetrofitAPI: RetrofitAPI // 레트로핏 api 객체
    lateinit var mCallAIReply : retrofit2.Call<Emotion> // Json 형식의 데이터를 요청하는 객체


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding = ActivityEmotionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setRetrofit()

        binding.button.setOnClickListener {
            // 버튼 클릭하면,
            binding.button.visibility = View.INVISIBLE // 버튼 숨김
            binding.predEmotion.visibility = View.INVISIBLE // 감정 예측 숨김

            binding.editTextDiary.visibility = View.VISIBLE // 일기

            val input = binding.editTextDiary.text.toString()
            Log.d("input:" , input) // input 확인용
            callEmotionDetect(input)
        }
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
            val param = emotionObj.param

            Log.d("test", "결과는 $param")

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
}

class ServerConnectErrorToast {

}
