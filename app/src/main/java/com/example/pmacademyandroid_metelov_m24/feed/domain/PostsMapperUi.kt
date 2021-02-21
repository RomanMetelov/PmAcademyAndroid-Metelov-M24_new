package com.example.pmacademyandroid_metelov_m24.feed.domain

import android.content.Context
import androidx.core.content.ContextCompat
import com.example.pmacademyandroid_metelov_m24.R
import com.example.pmacademyandroid_metelov_m24.feed.model.PostModel
import com.example.pmacademyandroid_metelov_m24.feed.model.PostModelUi
import com.example.pmacademyandroid_metelov_m24.shared.model.PostsErrors
import com.example.pmacademyandroid_metelov_m24.shared.model.UserType
import com.example.pmacademyandroid_metelov_m24.shared.async.Result
import javax.inject.Inject

class PostsMapperUi @Inject constructor(private val context: Context) {
    fun map(postsResult: Result<List<PostModel>, PostsErrors>): Result<List<PostModelUi>, String> {
        return postsResult.mapSuccess { posts ->
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

                PostModelUi(
                    userId = it.userId,
                    title = title,
                    body = it.body,
                    hasWarning = hasWarning,
                    isBanned = isBanned,
                    bgColorInt = bgColor
                )
            }
        }.mapError { postsErrors ->
            val errorStringRes = when (postsErrors) {
                PostsErrors.POSTS_NOT_LOADED -> R.string.error_loading_posts
                PostsErrors.ERROR -> R.string.error
            }

            context.getString(errorStringRes)
        }
    }
}