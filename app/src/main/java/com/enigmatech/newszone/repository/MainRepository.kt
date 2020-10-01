package com.enigmatech.newszone.repository


import com.enigmatech.newszone.cache.CacheMapper
import com.enigmatech.newszone.cache.NewsDao
import com.enigmatech.newszone.model.News
import com.enigmatech.newszone.network.NetworkMapper
import com.enigmatech.newszone.network.NewsApi
import com.enigmatech.newszone.utils.Constants
import com.enigmatech.newszone.utils.network.DataState
import kotlinx.coroutines.flow.flow

class MainRepository
constructor(
    var newsDao: NewsDao,
    var newsApi: NewsApi,
    var cacheMapper: CacheMapper,
    var networkMapper: NetworkMapper
) {

    private val TAG = "MainRepository"
    suspend fun getTopHeadlines(page: Int) = flow {

        emit(DataState.Loading)

        try {

            val networkTopHeadlines = newsApi.getTopHeadlines(
                Constants.NEWS_API_KEY,
                Constants.COUNTRY,
                page
            )


            val news = networkMapper.mapFromEntityList(networkTopHeadlines.articles)

            for (newsItem in news) {
                newsDao.insert(cacheMapper.mapToEntity(newsItem))
            }

            val cachedNews = newsDao.get()
            emit(DataState.Success(cacheMapper.mapFromEntityList(cachedNews)))


        } catch (e: Exception) {
            val cachedNews = newsDao.get()
            emit(DataState.Success(cacheMapper.mapFromEntityList(cachedNews)))
        }

    }

    suspend fun getCategoryHeadlines(category: String, page: Int) = flow {
        emit(DataState.Loading)

        try {

            val networkTopHeadlines = newsApi.getCategoryHeadlines(
                Constants.NEWS_API_KEY,
                Constants.COUNTRY,
                category,
                page
            )


            val news = networkMapper.mapFromEntityList(networkTopHeadlines.articles)

            emit(DataState.Success(networkMapper.mapFromEntityList(networkTopHeadlines.articles)))


        } catch (e: Exception) {
            emit(DataState.Error(e))
        }

    }

    suspend fun getSearchHeadlines(q: String, page: Int) = flow {
        emit(DataState.Loading)

        try {

            val networkTopHeadlines = newsApi.getSearchHeadlines(
                Constants.NEWS_API_KEY,
                q,
                page
            )

            val news = networkMapper.mapFromEntityList(networkTopHeadlines.articles)

            for (newsItem in news) {
                newsDao.insert(cacheMapper.mapToEntity(newsItem))
            }

            val cachedNews = newsDao.get()
            emit(DataState.Success(cacheMapper.mapFromEntityList(cachedNews)))

        } catch (e: Exception) {

            val cachedNews = newsDao.get()

            val searchedNews = cacheMapper.mapFromEntityList(cachedNews)
            val searchResult = mutableListOf<News>()

            for (newsItem in searchedNews) {
                if (newsItem.title.contains(q, true)) searchResult.add(newsItem)
            }

            emit(DataState.Success(searchResult))
        }

    }

}