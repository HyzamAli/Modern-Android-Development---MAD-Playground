package com.tut.mvvm_playground.network

enum class ResponseCode {
    NO_INTERNET,
    BAD_REQUEST, // 400
    UNAUTHORIZED_ACCESS, // 401
    METHOD_NOT_FOUND, // 404
    UNKNOWN_METHOD, // 405
    INTERNAL_SERVER_ERROR, // 500
    ERROR_UNKNOWN
}

sealed class Response<T> (
    val data: T? = null,
    val message: ResponseCode? = null
) {
    class Loading <T>(data: T? = null): Response<T>(data)
    class Success <T>(data: T) : Response<T>(data)
    class Failure <T>(message: ResponseCode, data: T? = null) : Response<T>(data, message)
}
