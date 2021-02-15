package com.example.pmacademyandroid_metelov_m24.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pmacademyandroid_metelov_m24.databinding.ActivityFeedBinding
import com.example.pmacademyandroid_metelov_m24.presentation.FeedAdapter
import com.example.pmacademyandroid_metelov_m24.presentation.FeedPresenter
import com.example.pmacademyandroid_metelov_m24.presentation.FeedView
import com.example.pmacademyandroid_metelov_m24.presentation.PostUiModel
import com.example.pmacademyandroid_metelov_m24.utils.FeedComponent

class FeedActivity : AppCompatActivity(), FeedView {

    private val binding: ActivityFeedBinding by lazy {
        ActivityFeedBinding.inflate(layoutInflater)
    }

    private val presenter: FeedPresenter by lazy { FeedComponent.createPresenter(this) }
    private val postsAdapter = FeedAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupRecyclerView()
        presenter.attachView(this)
    }

    private fun setupRecyclerView() {
        binding.rvPosts.run {
            layoutManager = LinearLayoutManager(this@FeedActivity)
            adapter = postsAdapter
            addItemDecoration(
                DividerItemDecoration(this@FeedActivity, RecyclerView.VERTICAL)
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun showFeed(posts: List<PostUiModel>) {
        binding.progressBar.visibility = View.GONE
        postsAdapter.submitList(posts)
    }

    override fun showError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show()
    }
}