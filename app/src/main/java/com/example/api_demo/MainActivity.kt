package com.example.api_demo

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.api_demo.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: NewsViewModel by viewModels()
    private val newsAdapter = NewsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        observeViewModel()

        // The variable name matches what you put in local.properties
        val apiKey = BuildConfig.NEWS_API_KEY
        viewModel.fetchNews(apiKey)

        // To add an API Key, first create one at https://newsapi.org/
        // Then add the API key to local.properties in the root directory
        // example: NEWS_API_KEY=abcd12345myapikey
    }

    private fun setupRecyclerView() {
        binding.newsRecyclerView.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    private fun observeViewModel() {
        viewModel.newsState.observe(this) { state ->
            when (state) {
                is NewsState.Loading -> {
                    binding.progressBar.isVisible = true
                }
                is NewsState.Success -> {
                    binding.progressBar.isVisible = false
                    newsAdapter.updateArticles(state.articles)
                }
                is NewsState.Error -> {
                    binding.progressBar.isVisible = false
                    Toast.makeText(this, state.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}