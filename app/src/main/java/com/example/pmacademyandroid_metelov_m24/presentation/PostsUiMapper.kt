package com.example.pmacademyandroid_metelov_m24.presentation

import android.content.Context
import androidx.core.content.ContextCompat
import com.example.pmacademyandroid_metelov_m24.R
import com.example.pmacademyandroid_metelov_m24.data.PostErrors
import com.example.pmacademyandroid_metelov_m24.domain.PostModel
import com.example.pmacademyandroid_metelov_m24.domain.UserType
import com.example.pmacademyandroid_metelov_m24.utils.Result




class PostsUiMapper(private val context: Context) {
    fun map(postResult: Result<List<PostModel>, PostErrors>): Result<List<PostUiModel>, String> {
        return postResult.mapSuccess { posts ->
            posts.map {
                val hasWarning = it.userType == UserType.WARNING
                val isBanned = it.userType == UserType.BANNED

                val bgColor = if (hasWarning) {
                    ContextCompat.getColor(context, R.color.warning_post_bg)
                } else {
                    ContextCompat.getColor(context, R.color.regular_post_bg)
                }

                val title =
                    if (isBanned) context.getString(R.string.user_banned, it.userId.toString())
                    else it.title

                PostUiModel(
                    userId = it.userId,
                    title = title,
                    body = it.body,
                    hasWarning = hasWarning,
                    isBanned = isBanned,
                    backgroundColor = bgColor
                )
            }
        }.mapError { postsErrors ->
            val errorStringRes = when (postsErrors) {
                PostErrors.POSTS_NOT_LOADED -> R.string.error_loading_posts
                PostErrors.ERROR -> R.string.error
            }

            context.getString(errorStringRes)
        }
    }
}