package com.example.pmacademyandroid_metelov_m24.presentation

import androidx.annotation.ColorInt

data class PostUiModel(
    val userId: Int,
    val title: String,
    val body: String,
    val hasWarning: Boolean,
    val isBanned: Boolean,
    @ColorInt val backgroundColor: Int
)
