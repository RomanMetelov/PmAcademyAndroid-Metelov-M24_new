package com.example.pmacademyandroid_metelov_m24.utils

import android.content.Context
import com.example.pmacademyandroid_metelov_m24.api.PostsService
import com.example.pmacademyandroid_metelov_m24.data.PostsRepository
import com.example.pmacademyandroid_metelov_m24.domain.PostsMapper
import com.example.pmacademyandroid_metelov_m24.domain.DisturberUsersRepository
import com.example.pmacademyandroid_metelov_m24.presentation.FeedPresenter
import com.example.pmacademyandroid_metelov_m24.presentation.PostsUiMapper
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object FeedComponent {
    fun createPresenter(context: Context): FeedPresenter {
        return FeedPresenter(
            postsRepository = PostsRepository(
                multithreading = Multithreading(context),
                postsService = createService(),
                postsMapper = PostsMapper(DisturberUsersRepository())
            ),
            postsUiMapper = PostsUiMapper(context)
        )
    }

    private fun createService(): PostsService {
        return Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PostsService::class.java)
    }
}