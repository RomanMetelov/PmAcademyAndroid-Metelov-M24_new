package com.example.pmacademyandroid_metelov_m24.domain

import com.example.pmacademyandroid_metelov_m24.data.Post
import com.example.pmacademyandroid_metelov_m24.data.PostErrors
import com.example.pmacademyandroid_metelov_m24.utils.Result

class PostsMapper(
    private val disturberUsersRepository: DisturberUsersRepository
) {
    fun map(postResult: Result<List<Post>, PostErrors>): Result<List<PostModel>, PostErrors> {
        val bannedUsers = disturberUsersRepository.getUsersWithBan()
        val userWithWarning = disturberUsersRepository.getUsersWithWarning()

        return postResult.mapSuccess { posts ->
            posts.map {
                when (it.userId) {
                    in bannedUsers -> PostModel(it.userId, "", "", UserType.BANNED)
                    in userWithWarning -> PostModel(
                        it.userId,
                        it.title,
                        it.body,
                        UserType.WARNING
                    )
                    else -> PostModel(it.userId, it.title, it.body, UserType.REGULAR)
                }
            }

        }
    }
}