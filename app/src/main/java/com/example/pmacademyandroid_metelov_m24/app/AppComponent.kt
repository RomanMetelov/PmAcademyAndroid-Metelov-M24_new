package com.example.pmacademyandroid_metelov_m24.app

import com.example.pmacademyandroid_metelov_m24.feed.ui.FeedFragment
import com.example.pmacademyandroid_metelov_m24.posting.PostingFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, FeedModule::class])
interface AppComponent {

    fun inject(fragment: FeedFragment)
    fun inject(fragment: PostingFragment)

}