package com.ramesh.virtusa.presentation.interactor
abstract class BaseUseCase<in Params, out T> {
    abstract suspend fun execute(params: Params): T
}

