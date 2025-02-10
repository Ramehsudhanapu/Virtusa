package com.ramesh.virtusa.utilities.common

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.TimeoutCancellationException
import retrofit2.HttpException

import java.io.IOException

open class BaseViewModel :ViewModel() {

    protected fun handleException(exception: Throwable): String {
        return when (exception) {
            is IOException -> AppStrings.NETWORK_ERROR
            is HttpException -> AppStrings.SERVER_ERROR+" ${exception.code()}"
            is TimeoutCancellationException -> AppStrings.REQUEST_TIME_OUT
            else -> exception.message ?: AppStrings.UNEXPECTED_ERRORE
        }
    }
}