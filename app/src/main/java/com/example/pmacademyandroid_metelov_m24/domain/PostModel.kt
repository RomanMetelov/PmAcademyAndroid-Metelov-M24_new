package com.example.pmacademyandroid_metelov_m24.domain

data class PostModel(
    val userId: Int,
    val title: String,
    val body: String,
    val userType: UserType
)
