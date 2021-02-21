package com.example.pmacademyandroid_metelov_m24.feed.domain

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pmacademyandroid_metelov_m24.feed.model.PostModelUi
import com.example.pmacademyandroid_metelov_m24.shared.async.CancelableOperation
import com.example.pmacademyandroid_metelov_m24.shared.repository.PostsRepository
import com.example.pmacademyandroid_metelov_m24.shared.async.Result
import javax.inject.Inject

class FeedViewModel @Inject constructor(
    private val postsRepository: PostsRepository,
    private val postsMapperUi: PostsMapperUi
) : ViewModel() {

    val viewState = MutableLiveData<FeedViewState>()

    private var cancelableOperation: CancelableOperation? = null

    fun fetchPosts() {
        viewState.value = FeedViewState.Loading

        cancelableOperation = postsRepository.getPosts()
            .map(postsMapperUi::map)
            .postOnMainThread(::updateFeed)
    }

    private fun updateFeed(result: Result<List<PostModelUi>, String>) {
        if (result.isError) {
            viewState.value = FeedViewState.PostsLoadError(result.errorResult)
        } else {
            viewState.value = FeedViewState.PostsLoadSuccess(result.successResult)
        }
    }
}

sealed class FeedViewState {
    object Loading : FeedViewState()
    data class PostsLoadSuccess(val posts: List<PostModelUi>) : FeedViewState()
    data class PostsLoadError(val error: String) : FeedViewState()
}