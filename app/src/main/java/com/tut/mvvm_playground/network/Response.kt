package com.tut.mvvm_playground.network

enum class ResponseCode {
    SUCCESS, // 200
    NO_INTERNET,
    BAD_REQUEST, // 400
    UNAUTHORIZED_ACCESS, // 401
    METHOD_NOT_FOUND, // 404
    UNKNOWN_METHOD, // 405
    INTERNAL_SERVER_ERROR, // 500
    ERROR_UNKNOWN
}

/**
 * This class represents a sealed class used to convey response to UI for single shot requests
 *
 * @param data indicates the data available
 * @param message indicates the responseCode related to the obtained data. Defaults to success
 */
sealed class Response<T> (
    val data: T? = null,
    val message: ResponseCode? = ResponseCode.SUCCESS
) {
    class Loading <T>(data: T? = null): Response<T>(data)
    class Success <T>(data: T) : Response<T>(data)
    class Failure <T>(message: ResponseCode, data: T? = null) : Response<T>(data, message)
}
