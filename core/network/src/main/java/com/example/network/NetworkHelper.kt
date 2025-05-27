package com.example.network

import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import kotlinx.io.IOException

class NetworkHelper {
    suspend inline fun <reified T> processCall(networkCall: () -> HttpResponse): T? {
        val response = try {
            networkCall.invoke()
        } catch (throwable: IOException) {
            throw NetworkException
        } catch (throwable: Throwable) {
            throw UnknownException("Unknown Exception: ${throwable.message}")
        }

        return handleResponse(response)
    }

    suspend inline fun <reified T> handleResponse(response: HttpResponse): T? {
        when (response.status.value) {
            in HttpStatusCode.OK.value..HttpStatusCode.MultipleChoices.value -> {
                return try {
                    response.body<T?>()
                } catch (throwable: Throwable) {
                    throw ParsingException("Parsing Error: ${throwable.message}")
                }
            }

            HttpStatusCode.Unauthorized.value -> throw UnauthorizedException()

            in HttpStatusCode.BadRequest.value..HttpStatusCode.InternalServerError.value -> {
                val errorBody = try {
                    response.body<BaseApiResponse<T>>()
                } catch (throwable: Throwable) {
                    throw ParsingException("Error Body Parsing Error: ${throwable.message}")
                }
                throw GeneralHttpException(
                    errorMsg = errorBody.statusMessage,
                    statusCode = errorBody.statusCode
                )
            }

            else -> throw UnknownException("Unknown HTTP Status Code: ${response.status.value}")
        }
    }
}
