package com.example.pmacademyandroid_metelov_m24.api

import com.example.pmacademyandroid_metelov_m24.data.Post
import retrofit2.Call
import retrofit2.http.GET

interface PostsService {
    @GET("/posts")
    fun getPostsList(): Call<List<Post>>
}