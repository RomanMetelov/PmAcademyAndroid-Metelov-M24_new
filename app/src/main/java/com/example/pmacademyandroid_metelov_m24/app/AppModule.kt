package com.example.pmacademyandroid_metelov_m24.app

import android.content.Context
import com.example.pmacademyandroid_metelov_m24.shared.PostsDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val context: Context) {

    @Provides
    @Singleton
    fun getContext(): Context = context

    @Provides
    @Singleton
    fun provideDatabase(): PostsDatabase = PostsDatabase

}