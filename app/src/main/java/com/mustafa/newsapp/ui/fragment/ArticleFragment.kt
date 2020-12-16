package com.mustafa.newsapp.ui.fragment

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.view.View.VISIBLE
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.mustafa.newsapp.R
import com.mustafa.newsapp.databinding.FragmentArticleBinding
import com.mustafa.newsapp.model.Article
import com.mustafa.newsapp.ui.viewmodel.ArticleViewModel
import com.mustafa.newsapp.util.autoCleared
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArticleFragment : Fragment(R.layout.fragment_article) {

    private val viewModel: ArticleViewModel by viewModels()

    private lateinit var article: Article

    private var binding by autoCleared<FragmentArticleBinding>()

    private val args: ArticleFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding = FragmentArticleBinding.bind(view)

        receiveArticleArgsAndOpenWebView()

        setupFloatingActionButton()
    }

    private fun receiveArticleArgsAndOpenWebView() {
        article = args.article
        binding.webView.apply {
            webViewClient = WebViewClientWithProgressBar(binding)
            article.url?.let { loadUrl(it) }
        }
    }

    private fun setupFloatingActionButton() {
        binding.fab.setOnClickListener {
            viewModel.saveArticle(article)
            Snackbar.make(requireView(), "Article saved successfully", Snackbar.LENGTH_SHORT)
                .apply {
                    setAction("Undo") {
                        viewModel.deleteArticle(article)
                    }
                    show()
                }
        }
    }

    private class WebViewClientWithProgressBar(val binding: FragmentArticleBinding) :
        WebViewClient() {
        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            binding.webViewProgressBar.visibility = VISIBLE
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            binding.webViewProgressBar.visibility = View.GONE
        }
    }

}