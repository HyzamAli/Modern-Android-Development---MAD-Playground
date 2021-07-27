package com.tut.mvvm_playground.network

import retrofit2.HttpException
import java.io.IOException

object NetworkResponseHandler {

    private fun  <T> getHttpVerb(responseCode: Int): Response.Failure<T> =
        when(responseCode) {
            400 -> Response.Failure(ResponseCode.BAD_REQUEST)
            401 -> Response.Failure(ResponseCode.UNAUTHORIZED_ACCESS)
            404 -> Response.Failure(ResponseCode.METHOD_NOT_FOUND)
            405 -> Response.Failure(ResponseCode.UNKNOWN_METHOD)
            500 -> Response.Failure(ResponseCode.INTERNAL_SERVER_ERROR)
            else -> Response.Failure(ResponseCode.ERROR_UNKNOWN)
        }

    // Should be called inside HttpExceptions to receive a ResponseWrapper [Failure] containing
    // the ResponseCode
    fun <T> handleResponse(throwable: Throwable): Response.Failure<T> =
        when (throwable) {
            is IOException -> Response.Failure(ResponseCode.NO_INTERNET)
            is HttpException -> getHttpVerb(throwable.code())
            else -> Response.Failure(ResponseCode.ERROR_UNKNOWN)
        }
}