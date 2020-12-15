package com.mustafa.newsapp.ui.fragment


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.mustafa.newsapp.R
import com.mustafa.newsapp.databinding.FragmentBreakingNewsBinding
import com.mustafa.newsapp.ui.adapter.LoadStateAdapter
import com.mustafa.newsapp.ui.adapter.NewsAdapter
import com.mustafa.newsapp.ui.viewmodel.BreakingNewsViewModel
import com.mustafa.newsapp.util.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class BreakingNewsFragment : Fragment(R.layout.fragment_breaking_news) {

    private val viewModel: BreakingNewsViewModel by viewModels()
    private var pagingAdapter by autoCleared<NewsAdapter>()

    private var binding by autoCleared<FragmentBreakingNewsBinding>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding = FragmentBreakingNewsBinding.bind(view)

        initAdapter()

        binding.retry.setOnClickListener { pagingAdapter.retry() }

        decorateRecyclerView()
        subscribeUI()

    }

    private fun subscribeUI() {

//         viewLifecycleOwner.lifecycleScope.launch {
//                  viewModel.breakingNewsStream.collect {
//                      pagingAdapter.submitData(it)
//                  }
//              }

        // It is Too IMPORTANT to use viewLifecycleOwner for fragments but Activities can use lifecycleScope directly
        viewModel.breakingNewsStream
            .onEach { pagingAdapter.submitData(it) }
            .launchIn(viewLifecycleOwner.lifecycleScope)

    }

    private fun initAdapter() {
        pagingAdapter = NewsAdapter {
            findNavController().navigate(
                BreakingNewsFragmentDirections.actionBreakingNewsFragmentToArticleFragment(
                    it
                )
            )
        }
        binding.rvBreakingNews.adapter = pagingAdapter.withLoadStateFooter(
            footer = LoadStateAdapter { pagingAdapter.retry() }
        )

        // This callback notifies us every time there's a change in the load state via a CombinedLoadStates object.
        // CombinedLoadStates gives us the load state for the PageSource
        // Or it gives us the load state for RemoteMediator needed for network and database case
        pagingAdapter.addLoadStateListener { loadState -> binding.loadState = loadState }
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