package com.example.door2door_app.networking.factory

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.door2door_app.user.domain.repository.preferences.IUserPreferences
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.engine.cio.endpoint
import io.ktor.client.plugins.HttpSend
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.plugin
import io.ktor.client.request.header
import io.ktor.client.request.headers
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.http.path
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.component.KoinComponent

object HttpClientFactory : KoinComponent {
    fun getHttpClient(
        preferences: IUserPreferences
    ): HttpClient {
        return HttpClient(CIO) {
            engine {
                endpoint {
                    connectTimeout = 5000
                }
            }
            install(Logging) {
                level = LogLevel.ALL
            }
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }
            defaultRequest {
                contentType(ContentType.Application.Json)
                url {
                    protocol = URLProtocol.HTTP
                    host = "10.0.1.49"
                    port = 8080
                    path("api/")
                }
            }
        }.apply {
            plugin(HttpSend).intercept { request ->
                request.header("Authorization", "Bearer ${preferences.getToken()}")
                execute(request)
            }
        }
    }
}