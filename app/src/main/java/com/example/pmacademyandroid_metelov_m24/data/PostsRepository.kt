package com.example.pmacademyandroid_metelov_m24.data

import com.example.pmacademyandroid_metelov_m24.api.PostsService
import com.example.pmacademyandroid_metelov_m24.domain.PostModel
import com.example.pmacademyandroid_metelov_m24.domain.PostsMapper
import com.example.pmacademyandroid_metelov_m24.utils.AsyncOperation
import com.example.pmacademyandroid_metelov_m24.utils.Multithreading
import com.example.pmacademyandroid_metelov_m24.utils.Result

class PostsRepository(
    private val multithreading: Multithreading,
    private val postsService: PostsService,
    private val postsMapper: PostsMapper
) {

    fun getPosts(): AsyncOperation<Result<List<PostModel>, PostErrors>> {
        val asyncOperation = multithreading.async<Result<List<Post>, PostErrors>> {
            val posts = postsService.getPostsList().execute().body()
                ?: return@async Result.error(PostErrors.POSTS_NOT_LOADED)

            return@async Result.success(posts)
        }
        return asyncOperation.map(postsMapper::map)
    }
}