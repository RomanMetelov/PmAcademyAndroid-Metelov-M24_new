package com.example.pmacademyandroid_metelov_m24.posting

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.pmacademyandroid_metelov_m24.AppApplication
import com.example.pmacademyandroid_metelov_m24.R
import com.example.pmacademyandroid_metelov_m24.databinding.PostingFragmentBinding
import com.example.pmacademyandroid_metelov_m24.posting.domain.NewPostViewModel
import com.example.pmacademyandroid_metelov_m24.posting.domain.NewPostViewState
import javax.inject.Inject

class PostingFragment : Fragment() {

    companion object {
        fun newInstance() = PostingFragment()
    }

    private var _binding: PostingFragmentBinding? = null
    private val binding get() = requireNotNull(_binding)

    @Inject
    lateinit var viewModel: NewPostViewModel

    private var titleText: String = ""
    private var bodyText: String = ""

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as AppApplication).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PostingFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObserver()
        setTextWatchers()
        setListeners()
    }

    private fun setTextWatchers() {
        binding.etPostTitle.addTextChangedListener(
            afterTextChanged = { text ->
                titleText = text?.toString() ?: ""
                checkInput()
            }
        )
        binding.etPostBody.addTextChangedListener(
            afterTextChanged = { text ->
                bodyText = text?.toString() ?: ""
                checkInput()
            }
        )
    }

    private fun setupObserver() {
        viewModel.viewState.observe(viewLifecycleOwner, {
            when (it) {
                is NewPostViewState.ValidPost -> {
                    binding.tilPostTitle.error = null
                    binding.tilPostBody.error = null
                    binding.btnSave.isEnabled = true
                }
                is NewPostViewState.InvalidPost -> {
                    binding.tilPostTitle.error = it.titleErrors.firstOrNull()
                    binding.tilPostBody.error = it.bodyErrors.firstOrNull()
                    binding.btnSave.isEnabled = false
                }
                is NewPostViewState.PostSubmitted -> {
                    goToFeed()
                }
            }
        })
    }

    private fun goToFeed() {
        findNavController().popBackStack(R.id.feedFragment, false)
    }

    private fun setListeners() {
        binding.btnSave.setOnClickListener {
            viewModel.submitPost(titleText, bodyText)
        }
    }

    private fun checkInput() {
        viewModel.checkInput(titleText, bodyText)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}