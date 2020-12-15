package com.mustafa.newsapp.ui.fragment

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.mustafa.newsapp.R
import com.mustafa.newsapp.databinding.FragmentSavedNewsBinding
import com.mustafa.newsapp.ui.adapter.LoadStateAdapter
import com.mustafa.newsapp.ui.adapter.NewsAdapter
import com.mustafa.newsapp.ui.viewmodel.SavedNewsViewModel
import com.mustafa.newsapp.util.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class SavedNewsFragment : Fragment(R.layout.fragment_saved_news) {

    private val viewModel: SavedNewsViewModel by viewModels()
    private var pagingAdapter by autoCleared<NewsAdapter>()
    private var binding by autoCleared<FragmentSavedNewsBinding>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding = FragmentSavedNewsBinding.bind(view)

        initAdapter()

        binding.retry.setOnClickListener { pagingAdapter.retry() }

        decorateRecyclerView()

        subscribeUI()


    }

    private fun subscribeUI() {

        // It is Too IMPORTANT to use viewLifecycleOwner for fragments but Activities can use lifecycleScope directly
        viewModel.savedNewsStream
            .onEach { pagingAdapter.submitData(it) }
            .launchIn(viewLifecycleOwner.lifecycleScope)

    }

    private fun initAdapter() {
        pagingAdapter = NewsAdapter {
            findNavController().navigate(
                SavedNewsFragmentDirections.actionSavedNewsFragmentToArticleFragment(
                    it
                )
            )
        }
        binding.rvSavedNews.adapter = pagingAdapter.withLoadStateFooter(
            footer = LoadStateAdapter { pagingAdapter.retry() }
        )

        // This callback notifies us every time there's a change in the load state via a CombinedLoadStates object.
        // CombinedLoadStates gives us the load state for the PageSource
        // Or it gives us the load state for RemoteMediator needed for network and database case
        pagingAdapter.addLoadStateListener { loadState -> binding.loadState = loadState }

        val itemTouchHelperCallBack = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.bindingAdapterPosition
                val article = pagingAdapter.getItemAt(position)

                if (article != null) {
                    viewModel.deleteArticle(article)
                    pagingAdapter.notifyItemRangeRemoved(position, 1)
//                    pagingAdapter.refresh()
//                    viewModel.invalidateSavedNewPagingSource()
                }
                Snackbar.make(requireView(), "Successfully deleted article", Snackbar.LENGTH_LONG)
            }

        }

        ItemTouchHelper(itemTouchHelperCallBack).apply {
            attachToRecyclerView(binding.rvSavedNews)
        }
    }

    private fun decorateRecyclerView() {
        val decoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        binding.rvSavedNews.addItemDecoration(decoration)
    }


}