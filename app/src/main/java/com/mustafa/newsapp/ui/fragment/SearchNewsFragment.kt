package com.mustafa.newsapp.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.mustafa.newsapp.R
import com.mustafa.newsapp.databinding.FragmentSearchNewsBinding
import com.mustafa.newsapp.ui.adapter.LoadStateAdapter
import com.mustafa.newsapp.ui.adapter.NewsAdapter
import com.mustafa.newsapp.ui.viewmodel.SearchNewsViewModel
import com.mustafa.newsapp.util.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SearchNewsFragment : Fragment(R.layout.fragment_search_news) {

    private val viewModel: SearchNewsViewModel by viewModels()
    private var pagingAdapter by autoCleared<NewsAdapter>()

    private var binding by autoCleared<FragmentSearchNewsBinding>()

    private var searchJob: Job? = null

    private var querySearch = "Covid19"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding = FragmentSearchNewsBinding.bind(view)

        initAdapter()

        updateRepoListFromInput(querySearch)

        initSearch()

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            query = querySearch
            itemCount = pagingAdapter.itemCount
        }

        binding.retry.setOnClickListener { pagingAdapter.retry() }
    }

    private fun initAdapter() {
        pagingAdapter = NewsAdapter {
            findNavController().navigate(
                SavedNewsFragmentDirections.actionSavedNewsFragmentToArticleFragment(
                    it
                )
            )
        }
        binding.rvSearchNews.adapter = pagingAdapter.withLoadStateFooter(
            footer = LoadStateAdapter { pagingAdapter.retry() }
        )

        // This callback notifies us every time there's a change in the load state via a CombinedLoadStates object.
        // CombinedLoadStates gives us the load state for the PageSource
        // Or it gives us the load state for RemoteMediator needed for network and database case
        pagingAdapter.addLoadStateListener { loadState -> binding.loadState = loadState }
    }


    private fun initSearch() {
        binding.toolbarSearch.searchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    querySearch = query
                    updateRepoListFromInput(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
    }

    private fun search(query: String) {
        // Make sure we cancel the previous job before creating a new one
        searchJob?.cancel()
        searchJob = viewLifecycleOwner.lifecycleScope.launch {
            viewModel.searchNews(query).collectLatest {
                pagingAdapter.submitData(it)
            }
        }
    }

    private fun updateRepoListFromInput(query: String) {
        query.trim().let {
            if (it.isNotEmpty()) {
                search(it)
            }
        }
    }


}