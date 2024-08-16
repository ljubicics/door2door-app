package com.example.door2door_app.networking.response

sealed class RepositoryResponse<out T> {
    /**
     * Represents successful network responses (2xx).
     */
    data class Success<T>(val body: T) : RepositoryResponse<T>()

    sealed class Error(val message: String) : RepositoryResponse<Nothing>() {
        /**
         * Represents server (50x) and client (40x) errors.
         */
        class HttpError(val code: Int, message: String) : Error(message)

        /**
         * Represent IOExceptions and connectivity issues.
         */
        object NetworkError : Error("Network error")

        /**
         * Represent SerializationExceptions.
         */
        object SerializationError : Error("Serialization error")
    }
}