package com.example.domain_layer.util

sealed class ResponseState<out T> {
    object NetworkError : ResponseState<Nothing>()
    data class Success<out T>(val body: T) : ResponseState<T>()
    data class Error<T>(val errorBody: String? = null) : ResponseState<T>()
}