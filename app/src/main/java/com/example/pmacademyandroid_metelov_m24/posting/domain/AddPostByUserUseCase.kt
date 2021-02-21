package com.example.pmacademyandroid_metelov_m24.posting.domain

import com.example.pmacademyandroid_metelov_m24.shared.model.PostData
import com.example.pmacademyandroid_metelov_m24.shared.repository.PostsRepository
import java.util.*
import javax.inject.Inject

class AddPostByUserUseCase @Inject constructor(
    private val postsRepository: PostsRepository
) {

    companion object {
        private const val USER_ID = 0
    }

    fun submit(title: String, body: String) {
        val post = PostData(
            userId = USER_ID,
            id = UUID.randomUUID().toString().hashCode(),
            title = title,
            body = body
        )
        postsRepository.submitLocalPost(post)
    }
}