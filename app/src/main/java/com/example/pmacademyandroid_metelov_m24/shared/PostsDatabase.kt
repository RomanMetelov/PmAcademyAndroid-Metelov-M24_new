package com.example.pmacademyandroid_metelov_m24.shared

import com.example.pmacademyandroid_metelov_m24.shared.model.PostData

object PostsDatabase {

    private val localPosts: MutableList<PostData> = mutableListOf()
    private val postsFromNet: MutableList<PostData> = mutableListOf()

    fun getPostsFromNet(newList: List<PostData>) {
        postsFromNet.clear()
        postsFromNet.addAll(newList)
    }

    fun addPostByUser(post: PostData) {
        localPosts.add(0, post)
    }

    fun getCachedPosts(): List<PostData> {
        return localPosts + postsFromNet
    }
}