package com.example.domain.util

sealed class ErrorBody{
    data class StringResource(val id: Int): ErrorBody()
    data class Message(val msg: String): ErrorBody()
}
