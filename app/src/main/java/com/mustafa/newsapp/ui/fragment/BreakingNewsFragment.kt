package com.mustafa.newsapp.ui.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import com.mustafa.newsapp.R
import com.mustafa.newsapp.databinding.FragmentBreakingNewsBinding
import com.mustafa.newsapp.ui.adapter.LoadStateAdapter
import com.mustafa.newsapp.ui.adapter.NewsAdapter
import com.mustafa.newsapp.ui.viewmodel.BreakingNewsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BreakingNewsFragment : Fragment() {

    private val viewModel: BreakingNewsViewModel by viewModels()
    private val pagingAdapter = NewsAdapter()

    lateinit var binding: FragmentBreakingNewsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_breaking_news,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.retry.setOnClickListener { pagingAdapter.retry() }

        decorateRecyclerView()
        initAdapter()
        subscribeUI()

    }

    private fun subscribeUI() {
        // It is Too IMPORTANT to use viewLifecycleOwner for fragments but Activities can use lifecycleScope directly
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.breakingNewsStream.collect {
                pagingAdapter.submitData(it)
            }
        }


    }

    private fun initAdapter() {
        /**
         * MyAdapter adapter1 = ...;
         * AnotherAdapter adapter2 = ...;
         * ConcatAdapter concatenated = new ConcatAdapter(adapter1, adapter2);
         * recyclerView.setAdapter(concatenated);
         */
        binding.rvBreakingNews.adapter = pagingAdapter.withLoadStateHeaderAndFooter(
            header = LoadStateAdapter { pagingAdapter.retry() },
            footer = LoadStateAdapter { pagingAdapter.retry() }
        )

        // This callback notifies us every time there's a change in the load state via a CombinedLoadStates object.
        // CombinedLoadStates gives us the load state for the PageSource
        // Or it gives us the load state for RemoteMediator needed for network and database case
        pagingAdapter.addLoadStateListener { loadState ->
            // Only show the list if refresh succeeds.
            binding.rvBreakingNews.isVisible =
                loadState.source.refresh is LoadState.NotLoading // Not Loading and Not Error -> Success
            // Show loading spinner during initial load or refresh.
            binding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading
            // Show the retry state if initial load or refresh fails.
            binding.retry.isVisible = loadState.source.refresh is LoadState.Error

            // Toast on any error, regardless of whether it came from RemoteMediator or PagingSource
            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error
            errorState?.let {
                Toast.makeText(
                    requireContext(),
                    "\uD83D\uDE28 Wooops ${it.error}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun decorateRecyclerView() {
        val decoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        binding.rvBreakingNews.addItemDecoration(decoration)
    }
}

/**
CombinedLoadStates allows us to get the load state for the 3 different types of load operations:
CombinedLoadStates.refresh - represents the load state for loading the PagingData for the first time.
CombinedLoadStates.prepend - represents the load state for loading data at the start of the list.
CombinedLoadStates.append - represents the load state for loading data at the end of the list.

 */