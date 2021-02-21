package com.example.pmacademyandroid_metelov_m24.app

import com.example.pmacademyandroid_metelov_m24.shared.PostsService
import com.example.pmacademyandroid_metelov_m24.shared.repository.DisturberUsersRepository
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class FeedModule {
    @Provides
    @Singleton
    fun getDisturberUsersRepository(): DisturberUsersRepository = DisturberUsersRepository()

    @Provides
    @Singleton
    fun provideRetrofit(gsonConverterFactory: GsonConverterFactory): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com")
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Provides
    @Singleton
    fun provideGsonConverter(): GsonConverterFactory {
        return GsonConverterFactory.create(
            GsonBuilder().setLenient().create()
        )
    }

    @Provides
    @Singleton
    fun providePostsService(retrofit: Retrofit): PostsService {
        return retrofit.create(PostsService::class.java)
    }

}