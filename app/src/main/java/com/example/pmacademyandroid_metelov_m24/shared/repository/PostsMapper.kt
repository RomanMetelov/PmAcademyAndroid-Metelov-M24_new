package com.example.pmacademyandroid_metelov_m24.shared.repository

import com.example.pmacademyandroid_metelov_m24.feed.model.PostModel
import com.example.pmacademyandroid_metelov_m24.shared.model.PostData

import com.example.pmacademyandroid_metelov_m24.shared.async.Result
import com.example.pmacademyandroid_metelov_m24.shared.model.PostsErrors
import com.example.pmacademyandroid_metelov_m24.shared.model.UserType
import javax.inject.Inject

class PostsMapper @Inject constructor(
    private val disturberUsersRepository: DisturberUsersRepository
) {
    fun map(postsResult: Result<List<PostData>, PostsErrors>): Result<List<PostModel>, PostsErrors> {
        val bannedUsers = disturberUsersRepository.getUsersWithBanList()
        val userWithWarning = disturberUsersRepository.getUsersWithWarningList()

        return postsResult.mapSuccess { posts ->
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