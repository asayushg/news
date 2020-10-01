package com.enigmatech.newszone.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.enigmatech.newszone.R
import com.enigmatech.newszone.model.Category
import com.enigmatech.newszone.model.News
import com.enigmatech.newszone.ui.adapters.CategoryAdapter
import com.enigmatech.newszone.ui.adapters.NewsAdapter
import com.enigmatech.newszone.ui.category.CategoryActivity
import com.enigmatech.newszone.utils.CustomTabHelper
import com.enigmatech.newszone.utils.SpaceItemDecoration
import com.enigmatech.newszone.utils.network.DataState.*
import com.enigmatech.newszone.utils.network.MainStateEvent
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
@AndroidEntryPoint
@SuppressLint("ClickableViewAccessibility")
class HomeActivity : AppCompatActivity(), CategoryAdapter.Interaction, NewsAdapter.Interaction {

    lateinit var categoryAdapter: CategoryAdapter
    lateinit var spaceItemDecoration: SpaceItemDecoration

    lateinit var newsAdapter: NewsAdapter

    var newsList = mutableListOf<News>()

    private val viewModel: HomeViewModel by viewModels()

    private var TAG = "HomeActivity"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        initUi()

        subscribeObservers()
        viewModel.setStateEvent(MainStateEvent.GetTopHeadlinesEvent, 1)

        checkIfNetworkAvail()

    }

    private fun checkIfNetworkAvail() {
        var have_WIFI = false
        var have_MobileData = false
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfos = connectivityManager.allNetworkInfo
        for (info in networkInfos) {
            if (info.typeName
                    .equals("WIFI", ignoreCase = true)
            ) if (info.isConnected) have_WIFI = true
            if (info.typeName
                    .equals("MOBILE DATA", ignoreCase = true)
            ) if (info.isConnected) have_MobileData = true
        }
        if (!have_WIFI && !have_MobileData) showSnackbar("No Internet Connection", searchView)
    }

    private fun subscribeObservers() {
        viewModel.dataState.observe(this, Observer { dataState ->

            when (dataState) {
                is Success<List<News>> -> {
                    //displayProgressBar(false)
                    shimmerFrameLayout.stopShimmerAnimation()
                    shimmerFrameLayout.visibility = View.GONE
                    Log.d(TAG, "subscribeObservers: ${dataState.data}")
                    appendNews(dataState.data)
                }
                is Error -> {
                    //displayProgressBar(false)
                    shimmerFrameLayout.stopShimmerAnimation()
                    shimmerFrameLayout.visibility = View.GONE
                    Log.d(TAG, "subscribeObservers: ${dataState.exception.message}")
                    showSnackbar(dataState.exception.message.toString(), searchView)
                }
                is Loading -> {
                    //displayProgressBar(true)
                    shimmerFrameLayout.startShimmerAnimation()
                    shimmerFrameLayout.visibility = View.VISIBLE
                }
            }

        })
    }

    private fun appendNews(data: List<News>) {
        newsList.addAll(data)
        newsAdapter.submitList(newsList)
    }

    private fun initUi() {


        searchView.setOnTouchListener(OnTouchListener { _, event ->
            val mRIGHT = 2
            if (event.action == MotionEvent.ACTION_UP) {
                try {
                    if (event.rawX >= searchView.right -
                        searchView.compoundDrawables[mRIGHT].bounds.width()
                    ) {
                        onClearClick()
                        return@OnTouchListener true
                    }
                } catch (e: Exception) {
                    Log.d(TAG, "initUi: " + e.message)
                }

            }
            false
        })

        newsCategoryRV.apply {
            layoutManager = LinearLayoutManager(
                this@HomeActivity,
                RecyclerView.HORIZONTAL,
                false
            )
            spaceItemDecoration = SpaceItemDecoration(10, 50)
            addItemDecoration(spaceItemDecoration)
            categoryAdapter = CategoryAdapter(this@HomeActivity)
            adapter = categoryAdapter
        }

        topHeadlinesRV.apply {
            layoutManager = LinearLayoutManager(this@HomeActivity)
            newsAdapter = NewsAdapter(this@HomeActivity)
            adapter = newsAdapter
        }

        searchView.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s != null) {
                    if (s.isNotEmpty()) {
                        searchView.setCompoundDrawablesWithIntrinsicBounds(
                            0,
                            0,
                            R.drawable.ic_close,
                            0
                        )
                    }
                }
            }
        })

        submitSearch.setOnClickListener { submitSearchQuery() }

        searchView.setOnEditorActionListener { _, actionId, _ ->
            var handled = false
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                submitSearchQuery()
                handled = true
            }
            handled
        }

        submitList()

    }

    private fun submitSearchQuery() {
        if (searchView.text.length >= 3) {
            val intent = Intent(this, CategoryActivity::class.java)
            intent.putExtra("search", searchView.text.toString())
            startActivity(intent)
        } else {
            showSnackbar("Enter at least 3 letters", searchView)
        }
    }

    private fun onClearClick() {
        if (searchView.text.isEmpty()) {
            searchView.clearFocus()
            searchView.hideKeyboard()
            searchView.setCompoundDrawablesWithIntrinsicBounds(
                0,
                0,
                0,
                0
            )
        } else {
            searchView.text = null
        }
    }

    private fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

    override fun onBackPressed() {
        if (searchView.hasFocus()) {
            onClearClick()
        } else {
            super.finishAfterTransition()
        }
    }

    override fun onCategorySelected(position: Int, item: Category) {
        val intent = Intent(this, CategoryActivity::class.java)
        intent.putExtra("category", item.name)
        startActivity(intent)
    }

    private fun submitList() {
        val list = mutableListOf<Category>()

        val namelist = listOf(
            "Business", "Health", "Movies", "Science", "Sports", "Tech"
        )

        val imageList = listOf(
            R.drawable.ic_business,
            R.drawable.ic_health,
            R.drawable.ic_movies,
            R.drawable.ic_science,
            R.drawable.ic_sports,
            R.drawable.ic_technology
        )

        for (i in 0..5) {
            list.add(
                Category(
                    imageList[i],
                    namelist[i]
                )
            )
        }

        categoryAdapter.submitList(list)

    }

    override fun onNewsSelected(position: Int, item: News) {
        val customTabHelper = CustomTabHelper()
        customTabHelper.openTab(this, item.url)
    }

    fun Snackbar.withColor(@ColorInt colorInt: Int): Snackbar {
        this.view.setBackgroundColor(colorInt)
        return this
    }

    fun showSnackbar(msg: String, view: View) {
        Snackbar
            .make(view, msg, Snackbar.LENGTH_LONG)
            .withColor(getColor(R.color.colorPrimary))
            .show()
    }

}