package com.example.network.networkClient

import com.example.network.BuildConfig
import com.example.network.NetworkConstants
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.accept
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

internal fun provideOkHttpClient(): HttpClient {
    return HttpClient(OkHttp) {
        // Install Content Negotiation
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
                explicitNulls = false
                allowStructuredMapKeys = true
            })
        }


        // Default request configuration
        defaultRequest {
            url(NetworkConstants.BASE_URL)
            header(NetworkConstants.Headers.AUTHORIZATION, BuildConfig.USER_TOKEN)
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }
    }
}
