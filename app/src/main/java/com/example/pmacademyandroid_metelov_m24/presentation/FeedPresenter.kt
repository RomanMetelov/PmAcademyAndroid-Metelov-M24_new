package com.example.pmacademyandroid_metelov_m24.presentation

import com.example.pmacademyandroid_metelov_m24.data.PostsRepository
import com.example.pmacademyandroid_metelov_m24.utils.CancelableOperation
import com.example.pmacademyandroid_metelov_m24.utils.Result

class FeedPresenter(
    private val postsRepository: PostsRepository,
    private val postsUiMapper: PostsUiMapper
) {
    private var view: FeedView? = null
    private var cancelableOperation: CancelableOperation? = null

    fun attachView(feedView: FeedView) {
        view = feedView

        cancelableOperation = postsRepository.getPosts()
            .map(postsUiMapper::map)
            .postOnMainThread(::showResult)
    }

    fun detachView() {
        view = null
        cancelableOperation?.cancel()
    }

    private fun showResult(result: Result<List<PostUiModel>, String>) {
        if (result.isError) {
            view?.showError(result.errorResult)
        } else {
            view?.showFeed(result.successResult)
        }
    }
}