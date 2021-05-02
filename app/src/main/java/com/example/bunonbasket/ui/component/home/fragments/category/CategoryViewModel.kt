package com.example.bunonbasket.ui.component.home.fragments.category

import androidx.lifecycle.*
import com.example.bunonbasket.data.models.category.Category
import com.example.bunonbasket.data.models.category.CategoryModel
import com.example.bunonbasket.data.models.category.SubCategoryModel
import com.example.bunonbasket.data.repository.remote.RemoteRepository
import com.example.bunonbasket.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by inan on 29/4/21
 */

@ExperimentalCoroutinesApi
@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val remoteRepository: RemoteRepository,
    private val state: SavedStateHandle
) : ViewModel(), LifecycleObserver {

    private val _categoryState: MutableLiveData<Resource<CategoryModel>> = MutableLiveData()
    private val _subCategoryState: MutableLiveData<Resource<SubCategoryModel>> = MutableLiveData()

    val categoryState: LiveData<Resource<CategoryModel>>
        get() = _categoryState

    val subCategoryState: LiveData<Resource<SubCategoryModel>>
        get() = _subCategoryState

    fun fetchRemoteEvents(categoryStateEvent: CategoryStateEvent) {
        viewModelScope.launch {
            when (categoryStateEvent) {
                is CategoryStateEvent.FetchCategories -> {
                    remoteRepository.fetchCategories()
                        .onEach { dataState ->
                            _categoryState.value = dataState
                        }.launchIn(viewModelScope)
                }

                is CategoryStateEvent.FetchSubCategories -> {
                    remoteRepository.fetchSubCategories(categoryStateEvent.id)
                        .onEach { dataState ->
                            _subCategoryState.value = dataState
                        }.launchIn(viewModelScope)
                }

                is CategoryStateEvent.None -> {

                }
            }
        }
    }

    fun onCategoryClicked(category: Category) {
        fetchRemoteEvents(CategoryStateEvent.FetchSubCategories(category.id.toString()))
    }
}

sealed class CategoryStateEvent {

    object FetchCategories : CategoryStateEvent()

    data class FetchSubCategories(val id: String) : CategoryStateEvent()

    object None : CategoryStateEvent()
}