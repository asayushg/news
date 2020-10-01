package com.enigmatech.newszone.utils.network

sealed class MainStateEvent {

    object GetTopHeadlinesEvent : MainStateEvent()

    object GetCategoryEvent : MainStateEvent()

    object GetSearchEvent : MainStateEvent()

    object None : MainStateEvent()
}