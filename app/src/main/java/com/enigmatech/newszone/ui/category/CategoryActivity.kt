package com.enigmatech.newszone.ui.category

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.enigmatech.newszone.R
import com.enigmatech.newszone.model.News
import com.enigmatech.newszone.ui.adapters.NewsAdapter
import com.enigmatech.newszone.utils.CustomTabHelper
import com.enigmatech.newszone.utils.network.DataState
import com.enigmatech.newszone.utils.network.MainStateEvent
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_category.*
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class CategoryActivity : AppCompatActivity(), NewsAdapter.Interaction {

    lateinit var newsAdapter: NewsAdapter
    private val viewModel: CategoryViewModel by viewModels()
    private val TAG = "CategoryActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        initUi()

    }


    private fun initUi() {
        backBtn.setOnClickListener { super.onBackPressed() }

        if (!intent.getStringExtra("category").isNullOrEmpty()) {
            var s = intent.getStringExtra("category").toString()
            if (s == "Tech") s = "Technology"
            categoryTitle.text = s

            subscribeObservers()
            if (s == "Movies") s = "entertainment"
            viewModel.setStateEvent(MainStateEvent.GetCategoryEvent, s.toLowerCase(), 1)
        }

        if (!intent.getStringExtra("search").isNullOrEmpty()) {
            categoryTitle.text = getString(R.string.search)
            val s = "Showing search results for ${intent.getStringExtra("search")}"
            searchQuery.text = s
            searchQuery.visibility = View.VISIBLE
            subscribeObservers()
            viewModel.setStateEvent(
                MainStateEvent.GetSearchEvent,
                intent.getStringExtra("search").toString().toLowerCase(),
                1
            )

        } else {
            searchQuery.visibility = View.GONE
        }

        newsResultRV.apply {
            layoutManager = LinearLayoutManager(this@CategoryActivity)
            newsAdapter = NewsAdapter(this@CategoryActivity)
            adapter = newsAdapter
        }

    }

    private fun subscribeObservers() {
        viewModel.dataState.observe(this, Observer { dataState ->

            when (dataState) {
                is DataState.Success<List<News>> -> {
                    //displayProgressBar(false)
                    shimmerFrameLayoutCategory.stopShimmerAnimation()
                    shimmerFrameLayoutCategory.visibility = View.GONE
                    Log.d(TAG, "subscribeObservers: ${dataState.data}")
                    appendNews(dataState.data)
                }
                is DataState.Error -> {
                    //displayProgressBar(false)
                    shimmerFrameLayoutCategory.stopShimmerAnimation()
                    shimmerFrameLayoutCategory.visibility = View.GONE
                    Log.d(TAG, "subscribeObservers: ${dataState.exception.message}")
                    //displayError(dataState.exception.message)
                }
                is DataState.Loading -> {
                    //displayProgressBar(true)
                    shimmerFrameLayoutCategory.startShimmerAnimation()
                    shimmerFrameLayoutCategory.visibility = View.VISIBLE
                }
            }

        })
    }

    private fun appendNews(data: List<News>) {
        if (data.isEmpty()) showSnackbar("Can't find news", backBtn)
        newsAdapter.submitList(data)
    }

    override fun onNewsSelected(position: Int, item: News) {
        val customTabHelper = CustomTabHelper()
        customTabHelper.openTab(this, item.url)
    }

    fun showSnackbar(msg: String, view: View) {
        Snackbar
            .make(view, msg, Snackbar.LENGTH_LONG)
            .withColor(getColor(R.color.colorPrimary))
            .show()
    }

    fun Snackbar.withColor(@ColorInt colorInt: Int): Snackbar {
        this.view.setBackgroundColor(colorInt)
        return this
    }


}