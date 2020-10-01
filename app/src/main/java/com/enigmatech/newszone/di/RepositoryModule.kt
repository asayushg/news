package com.enigmatech.newszone.di


import com.enigmatech.newszone.cache.CacheMapper
import com.enigmatech.newszone.cache.NewsDao
import com.enigmatech.newszone.network.NetworkMapper
import com.enigmatech.newszone.network.NewsApi
import com.enigmatech.newszone.repository.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideMainRepository(
        newsDao: NewsDao,
        newsApi: NewsApi,
        cacheMapper: CacheMapper,
        networkMapper: NetworkMapper
    ): MainRepository {
        return MainRepository(
            newsDao,
            newsApi,
            cacheMapper,
            networkMapper
        )
    }

}