package com.mp3.neulbo.client

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitAPI {
    @GET("/echo_call/{param}") // 서버에 GET 요청을 할 주소 입력
    fun getAIReply(@Path("param") chatText: String) : Call<Emotion> // 입력으로 chatText 를 서버로 넘기고, 서버에서 답장을 가져오는 메소드
}