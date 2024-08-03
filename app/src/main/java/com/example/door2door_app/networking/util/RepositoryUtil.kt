package com.example.door2door_app.networking.util

import com.example.door2door_app.networking.response.ErrorResponse
import com.example.door2door_app.networking.response.RepositoryResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.request
import io.ktor.client.statement.HttpResponse
import io.ktor.utils.io.errors.IOException
import kotlinx.serialization.SerializationException


/**
 * Safely wraps ktor request and [RepositoryResponse.Success] if response is successful,
 * otherwise [RepositoryResponse.Error]
 *
 * @param T type to which response body should be cast to.
 * @param block api call
 * @return [RepositoryResponse.Success] if response is successful, otherwise [RepositoryResponse.Error]
 */
suspend inline fun <reified T> HttpClient.safeRequest(
    block: HttpRequestBuilder.() -> Unit,
): RepositoryResponse<T> =
    try {
        val response = request { block() }
        if (response.status.value == 200) {
            RepositoryResponse.Success(response.body())
        } else {
            RepositoryResponse.Error.HttpError(
                response.status.value,
                response.errorBody()?.message.toString()
            )
        }
    } catch (e: ClientRequestException) {
        RepositoryResponse.Error.HttpError(
            e.response.status.value,
            e.errorBody()?.message.toString()
        )
    } catch (e: ServerResponseException) {
        RepositoryResponse.Error.HttpError(
            e.response.status.value,
            e.errorBody()?.message.toString()
        )
    } catch (e: IOException) {
        RepositoryResponse.Error.NetworkError
    } catch (e: SerializationException) {
        RepositoryResponse.Error.SerializationError
    } catch (e: Exception) {
        RepositoryResponse.Error.HttpError(code = 0, message = e.message.toString())
    }

/**
 * Creates [ServerErrorResponse] from response exception error body.
 *
 * @return [ServerErrorResponse] from error body.
 */
suspend inline fun ResponseException.errorBody(): ErrorResponse? =
    try {
        response.body() as ErrorResponse
    } catch (e: SerializationException) {
        null
    }

/**
 * Casts response error body to [ServerErrorResponse].
 *
 * @return [ServerErrorResponse] casted from response body.
 */
suspend inline fun HttpResponse.errorBody(): ErrorResponse? = try {
    body() as ErrorResponse
} catch (e: SerializationException) {
    null
}

