package com.example.bunonbasket.ui.component.home.fragments.category

import androidx.lifecycle.*
import com.example.bunonbasket.data.models.base.BaseModel
import com.example.bunonbasket.data.models.base.BasePaginatedModel
import com.example.bunonbasket.data.models.category.*
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

    private val _categoryState: MutableLiveData<Resource<BaseModel<Category>>> = MutableLiveData()
    private val _subCategoryState: MutableLiveData<Resource<BaseModel<SubCategory>>> = MutableLiveData()
    private val _productState: MutableLiveData<Resource<BasePaginatedModel<PaginatedModel>>> = MutableLiveData()

    val categoryState: LiveData<Resource<BaseModel<Category>>>
        get() = _categoryState

    val subCategoryState: LiveData<Resource<BaseModel<SubCategory>>>
        get() = _subCategoryState

    val productState: LiveData<Resource<BasePaginatedModel<PaginatedModel>>>
        get() = _productState

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

                is CategoryStateEvent.FetchProductBySubCategories -> {
                    remoteRepository.fetchProductBySubCategories(
                        categoryStateEvent.id,
                        categoryStateEvent.page,
                        categoryStateEvent.perPage
                    ).onEach { dataState ->
                        _productState.value = dataState
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

    fun onSubCategoryClicked(subCategory: SubCategory?, page: Int, perPage: Int) {
        fetchRemoteEvents(
            CategoryStateEvent.FetchProductBySubCategories(
                subCategory?.id.toString(),
                page,
                perPage
            )
        )
    }
}

sealed class CategoryStateEvent {

    object FetchCategories : CategoryStateEvent()

    data class FetchSubCategories(val id: String) : CategoryStateEvent()

    data class FetchProductBySubCategories(val id: String, val page: Int, val perPage: Int) :
        CategoryStateEvent()

    object None : CategoryStateEvent()
}