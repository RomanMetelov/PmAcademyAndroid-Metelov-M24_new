package com.example.pmacademyandroid_metelov_m24.shared.model

import com.google.gson.annotations.SerializedName

data class PostData(
    @SerializedName("body")
    val body: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("userId")
    val userId: Int
)