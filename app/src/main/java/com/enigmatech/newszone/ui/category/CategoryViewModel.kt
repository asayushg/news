package com.enigmatech.newszone.ui.category

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.enigmatech.newszone.model.News
import com.enigmatech.newszone.repository.MainRepository
import com.enigmatech.newszone.utils.network.DataState
import com.enigmatech.newszone.utils.network.MainStateEvent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch


@ExperimentalCoroutinesApi
class CategoryViewModel
@ViewModelInject
constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val mainRepository: MainRepository
) : ViewModel() {

    private val _dataState: MutableLiveData<DataState<List<News>>> = MutableLiveData()

    val dataState: LiveData<DataState<List<News>>>
        get() = _dataState

    fun setStateEvent(mainStateEvent: MainStateEvent, category: String, page: Int) {
        viewModelScope.launch {
            when (mainStateEvent) {
                is MainStateEvent.GetCategoryEvent -> {
                    mainRepository.getCategoryHeadlines(category, page)
                        .onEach { dataState ->
                            _dataState.value = dataState
                        }
                        .launchIn(viewModelScope)
                }

                is MainStateEvent.GetSearchEvent -> {
                    mainRepository.getSearchHeadlines(category, page)
                        .onEach { dataState ->
                            _dataState.value = dataState
                        }
                        .launchIn(viewModelScope)
                }

            }
        }
    }

}