package com.example.pmacademyandroid_metelov_m24.shared.repository

import com.example.pmacademyandroid_metelov_m24.feed.model.PostModel
import com.example.pmacademyandroid_metelov_m24.shared.PostsService
import com.example.pmacademyandroid_metelov_m24.shared.async.AsyncOperation
import com.example.pmacademyandroid_metelov_m24.shared.async.Multithreading
import com.example.pmacademyandroid_metelov_m24.shared.PostsDatabase
import com.example.pmacademyandroid_metelov_m24.shared.model.PostData
import com.example.pmacademyandroid_metelov_m24.shared.model.PostsErrors
import com.example.pmacademyandroid_metelov_m24.shared.async.Result
import javax.inject.Inject

class PostsRepository @Inject constructor(
    private val multithreading: Multithreading,
    private val postsService: PostsService,
    private val postsMapper: PostsMapper,
    private val database: PostsDatabase
) {

    fun getPosts(): AsyncOperation<Result<List<PostModel>, PostsErrors>> {
        val asyncOperation = multithreading.async<Result<List<PostData>, PostsErrors>> {
            val posts = postsService.getPosts().execute().body()
                ?: return@async Result.error(PostsErrors.POSTS_NOT_LOADED)

            database.getPostsFromNet(posts)
            return@async Result.success(database.getCachedPosts())
        }
        return asyncOperation.map(postsMapper::map)
    }

    fun submitLocalPost(post: PostData) {
        database.addPostByUser(post)
    }
}