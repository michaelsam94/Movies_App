package com.michael.moviesapp.data

sealed class ErrorType {
    object NoInternet : ErrorType()
    object ServerError : ErrorType()
    data class Unknown(val message: String) : ErrorType()
}