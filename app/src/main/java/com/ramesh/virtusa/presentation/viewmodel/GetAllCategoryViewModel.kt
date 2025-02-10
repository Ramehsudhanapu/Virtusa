package com.ramesh.virtusa.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramesh.virtusa.domain.usecase.GetAllCategoryUseCase
import com.ramesh.virtusa.presentation.state.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class GetAllCategoryViewModel @Inject constructor(private val allCategoryUseCase: GetAllCategoryUseCase) :
    ViewModel() {

    private val _uiState = MutableStateFlow<UIState<List<String>>>(UIState.Loading)

    val uiState: StateFlow<UIState<List<String>>> = _uiState.asStateFlow()


    fun getAllCategories() {
        viewModelScope.launch  {
            try {
                withContext(Dispatchers.IO) {
                     allCategoryUseCase.execute(Unit).flowOn(
                         Dispatchers.IO
                     ).onEach {  categories ->
                        withContext(Dispatchers.Main)
                        {
                            _uiState.value = UIState.Success(categories)
                        }
                    }.launchIn(this)
                }

        }catch (Exception: Exception) {
            withContext(Dispatchers.Main)
            {
                _uiState.value = UIState.Error(Exception.message ?: "Unknown error")
            }

        }
            }

    }


}