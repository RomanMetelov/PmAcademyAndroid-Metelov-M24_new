package com.example.pmacademyandroid_metelov_m24.feed.model

import androidx.annotation.ColorInt

data class BannedPostModelUi(
    val userId: Int,
    val title: String,
    val isBanned: Boolean,
    @ColorInt val bgColorInt: Int
)

