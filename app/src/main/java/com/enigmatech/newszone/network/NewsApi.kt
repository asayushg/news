package com.enigmatech.newszone.network

import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("top-headlines")
    suspend fun getTopHeadlines(

        @Query("apiKey") api_key : String,
        @Query("country") country : String,
        @Query("page") page : Int

    ) : NewsNetworkEntity

    @GET("top-headlines")
    suspend fun getCategoryHeadlines(

        @Query("apiKey") api_key : String,
        @Query("country") country : String,
        @Query("category") category : String,
        @Query("page") page : Int

    ) : NewsNetworkEntity

    @GET("top-headlines")
    suspend fun getSearchHeadlines(

        @Query("apiKey") api_key : String,
        @Query("q") q : String,
        @Query("page") page : Int

    ) : NewsNetworkEntity

}