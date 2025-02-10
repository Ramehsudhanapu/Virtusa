package com.ramesh.virtusa.presentation.state

sealed  class UIState<out T > {
    object Loading : UIState<Nothing>()
    data class Success<T>(val data: T) : UIState<T>()
    data class Error(val message: String) : UIState<Nothing>()



}