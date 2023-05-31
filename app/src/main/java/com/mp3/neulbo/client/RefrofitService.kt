package com.mp3.neulbo.client

import retrofit2.Call
import retrofit2.http.*

interface RetrofitService {
    @GET("posts/{info}")
    fun  // url을 제외한 End Point
            getResults(@Path("info") body: String?): Call<Emotion?>? // get방식

    @POST("posts/post")
    fun postInfo(@Header("token") token: String?, @Body body: String?): Call<Emotion?>? // post방식
}

