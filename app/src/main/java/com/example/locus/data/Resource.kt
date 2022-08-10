package com.example.locus.data


sealed class Resource<out T> {

    data class Success<out T>(val value: T) : Resource<T>()
    data class Failure(
        val errorCode: Int?,
        val error: String?
    ) : Resource<Nothing>()

    object Loading : Resource<Nothing>()
}