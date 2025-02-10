package com.ramesh.virtusa.presentation.viewmodel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramesh.virtusa.data.model.ProductResponse
import com.ramesh.virtusa.domain.usecase.GetProductsByCategoryNameUseCase
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
class GetProductsByCategoryNameViewModel @Inject constructor(private val getProductsByCategoryNameUseCase: GetProductsByCategoryNameUseCase) :
    ViewModel() {

        private  val _uiState= MutableStateFlow<UIState<List<ProductResponse>>>(UIState.Loading)
        val uiState: StateFlow<UIState<List<ProductResponse>>> = _uiState

     fun getProductsByCategoryName(categoryName: String) {
         viewModelScope.launch {
             try {
                  withContext(Dispatchers.IO)
                  {
                      getProductsByCategoryNameUseCase.execute(categoryName).flowOn(Dispatchers.IO).
                      onEach { products ->
                          withContext(Dispatchers.Main)
                          {
                              _uiState.value = UIState.Success(products)
                          }
                      }.launchIn(this)

                  }
             }catch (Exception: Exception)
             {
                 withContext(Dispatchers.Main)
                 {
                     _uiState.value = UIState.Error(Exception.message ?: "Unknown error")
                 }
             }

         }
     }
}