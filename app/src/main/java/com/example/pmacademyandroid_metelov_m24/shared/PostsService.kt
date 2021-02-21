package com.example.pmacademyandroid_metelov_m24.shared

import com.example.pmacademyandroid_metelov_m24.shared.model.PostData
import retrofit2.Call
import retrofit2.http.GET

interface PostsService {
    @GET("/posts")
    fun getPosts(): Call<List<PostData>>
}