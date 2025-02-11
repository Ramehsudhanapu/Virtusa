package com.ramesh.virtusa.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.ramesh.virtusa.data.model.ProductResponse
import com.ramesh.virtusa.domain.usecase.GetProductsByCategoryNameUseCase
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
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class GetProductsByCategoryNameViewModel @Inject constructor(private val getProductsByCategoryNameUseCase: GetProductsByCategoryNameUseCase) :
    BaseViewModel() {

    private val _uiState = MutableStateFlow<UIState<List<ProductResponse>>>(UIState.Loading)
    val uiState: StateFlow<UIState<List<ProductResponse>>> = _uiState.asStateFlow()

    // Define a CoroutineExceptionHandler
    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        _uiState.value = UIState.Error(handleException(exception))
    }
    fun getProductsByCategoryName(categoryName: String) {
        viewModelScope.launch(exceptionHandler) {
            getProductsByCategoryNameUseCase.execute(categoryName).
            flowOn(Dispatchers.IO)
                .onEach { products ->
                    _uiState.value = UIState.Success(products)
                }
                .catch { exception ->
                    _uiState.value = UIState.Error(handleException(exception))
                }.collect()

        }


    }
}
