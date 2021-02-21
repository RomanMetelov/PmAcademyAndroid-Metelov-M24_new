package com.example.pmacademyandroid_metelov_m24.feed.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pmacademyandroid_metelov_m24.AppApplication
import com.example.pmacademyandroid_metelov_m24.R
import com.example.pmacademyandroid_metelov_m24.databinding.FeedFragmentBinding
import com.example.pmacademyandroid_metelov_m24.feed.domain.FeedViewModel
import com.example.pmacademyandroid_metelov_m24.feed.domain.FeedViewState
import javax.inject.Inject

class FeedFragment : Fragment() {

    companion object {
        fun newInstance() = FeedFragment()
    }

    @Inject
    lateinit var feedViewModel: FeedViewModel

    private lateinit var feedAdapter: FeedAdapter

    private var _binding: FeedFragmentBinding? = null
    private val binding get() = requireNotNull(_binding)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as AppApplication).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FeedFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRv()
        setListeners()
        setupObserver()
        feedViewModel.fetchPosts()
    }

    private fun setupObserver() {
        feedViewModel.viewState.observe(viewLifecycleOwner, {
            when (it) {
                is FeedViewState.Loading -> {
                    binding.rvPosts.visibility = View.GONE
                    binding.progressBar.visibility = View.VISIBLE
                }
                is FeedViewState.PostsLoadError -> {
                    Toast.makeText(context, it.error, Toast.LENGTH_LONG).show()
                }
                is FeedViewState.PostsLoadSuccess -> {
                    binding.rvPosts.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE
                    feedAdapter.submitList(it.posts)
                }
            }
        })
    }

    private fun setupRv() {
        feedAdapter = FeedAdapter()
        binding.rvPosts.apply {
            adapter = feedAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(
                DividerItemDecoration(context, RecyclerView.VERTICAL)
            )
        }
    }

    private fun setListeners() {
        binding.btnAddPost.setOnClickListener {
            findNavController().navigate(R.id.action_feedFragment_to_newPostFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}