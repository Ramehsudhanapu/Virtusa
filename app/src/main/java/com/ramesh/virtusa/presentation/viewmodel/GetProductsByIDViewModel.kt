package com.ramesh.virtusa.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramesh.virtusa.data.model.Product
import com.ramesh.virtusa.domain.usecase.GetProductsByIDUseCase
import com.ramesh.virtusa.presentation.state.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class GetProductsByIDViewModel  @Inject constructor(private val getProductsByIDUseCase: GetProductsByIDUseCase)
    : ViewModel() {

    private val _uiState = MutableStateFlow<UIState<Product>>(UIState.Loading)
    val uiState: StateFlow<UIState<Product>> = _uiState

    fun getProductByID(productId: Int) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO)
                {
                    getProductsByIDUseCase.execute(productId).flowOn(Dispatchers.IO).
                            onEach { products ->
                                withContext(Dispatchers.Main)
                                {
                                    _uiState.value = UIState.Success(products)
                                }
                            }.launchIn(this)

                }

            } catch (Exception: Exception) {

                withContext(Dispatchers.Main)
                {
                    _uiState.value = UIState.Error(Exception.message ?: "Unknown error")
                }

            }
        }
    }
}