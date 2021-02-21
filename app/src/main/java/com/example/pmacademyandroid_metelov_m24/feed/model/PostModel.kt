package com.example.pmacademyandroid_metelov_m24.feed.model

import com.example.pmacademyandroid_metelov_m24.shared.model.UserType

data class PostModel(
    val userId: Int,
    val title: String,
    val body: String,
    val userType: UserType
)