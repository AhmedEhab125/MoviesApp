package com.example.rabbit.core.network.networkClient

import android.annotation.SuppressLint
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
import okhttp3.OkHttpClient
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

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
            header("Authorization", BuildConfig.USER_TOKEN)
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }
    }
}
