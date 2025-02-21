package com.michael.moviesapp.data.model

import com.michael.moviesapp.data.ErrorType

sealed class ResultState<out T> {
    data class Success<T>(val data: T) : ResultState<T>()
    data class Error(val errorType: ErrorType) : ResultState<Nothing>()
    object Loading : ResultState<Nothing>()
}