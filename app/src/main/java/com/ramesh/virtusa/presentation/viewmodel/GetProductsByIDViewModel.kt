package com.ramesh.virtusa.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.ramesh.virtusa.data.model.Product
import com.ramesh.virtusa.domain.usecase.GetProductsByIDUseCase
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
class GetProductsByIDViewModel @Inject constructor(private val getProductsByIDUseCase: GetProductsByIDUseCase) :
    BaseViewModel() {

    private val _uiState = MutableStateFlow<UIState<Product>>(UIState.Loading)
    val uiState: StateFlow<UIState<Product>> = _uiState.asStateFlow()

    // Define a CoroutineExceptionHandler
    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        _uiState.value = UIState.Error(handleException(exception))
    }

    fun getProductByID(productId: Int) {
        viewModelScope.launch(exceptionHandler) {

            getProductsByIDUseCase.execute(productId).flowOn(Dispatchers.IO).
            onEach { products ->
                _uiState.value = UIState.Success(products)

            }
                .catch { exception ->
                    _uiState.value = UIState.Error(handleException(exception))
                }.collect()

        }

    }
}
