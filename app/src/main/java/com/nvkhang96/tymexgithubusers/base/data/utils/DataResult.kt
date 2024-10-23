package com.nvkhang96.tymexgithubusers.base.data.utils

sealed class DataResult<out T: Any> {

    data class Success<out T : Any>(val data: T) : DataResult<T>()

    data class Error<out T : Any>(
        val errorCode: String? = null,
        val errorMessage: String? = null,
        val data: T? = null,
        val exception: Exception? = null,
    ) : DataResult<T>()
}