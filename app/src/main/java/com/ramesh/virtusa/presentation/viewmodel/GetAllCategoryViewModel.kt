package com.ramesh.virtusa.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.ramesh.virtusa.domain.usecase.GetAllCategoryUseCase
import com.ramesh.virtusa.presentation.state.UIState
import com.ramesh.virtusa.utilities.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GetAllCategoryViewModel @Inject constructor(private val allCategoryUseCase: GetAllCategoryUseCase

   ) :
    BaseViewModel() {

    private val _uiState = MutableStateFlow<UIState<List<String>>>(UIState.Loading)

    val uiState: StateFlow<UIState<List<String>>> = _uiState.asStateFlow()

    // Define a CoroutineExceptionHandler
    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        _uiState.value = UIState.Error(handleException(exception))
    }

    fun getAllCategories() {
        viewModelScope.launch(exceptionHandler) {
            allCategoryUseCase.execute(Unit)
                .flowOn(Dispatchers.IO)
                .onEach { categories ->
                    _uiState.value = UIState.Success(categories)
                }
                .catch { exception ->
                    _uiState.value = UIState.Error(handleException(exception))
                }
                .collect()
        }
    }
}