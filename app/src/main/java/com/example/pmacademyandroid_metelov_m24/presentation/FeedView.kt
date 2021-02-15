package com.example.pmacademyandroid_metelov_m24.presentation

interface FeedView {
    fun showFeed(posts: List<PostUiModel>)
    fun showError(error: String)
}