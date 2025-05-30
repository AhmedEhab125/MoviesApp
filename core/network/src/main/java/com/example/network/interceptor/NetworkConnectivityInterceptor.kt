package com.example.network.interceptor

import com.example.network.NoNetworkException
import com.example.network.connectivity.INetworkConnectivityChecker
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpClientPlugin
import io.ktor.client.request.HttpRequestPipeline
import io.ktor.util.AttributeKey

/**
 * Ktor interceptor that checks network connectivity before making HTTP requests
 * Throws NoNetworkException if no network connection is available
 */
class NetworkConnectivityInterceptor(
    private val networkChecker: INetworkConnectivityChecker
) {

    /**
     * Ktor plugin configuration for network connectivity checking
     */
    companion object : HttpClientPlugin<Config, NetworkConnectivityInterceptor> {
        override val key =
            AttributeKey<NetworkConnectivityInterceptor>("NetworkConnectivityInterceptor")

        override fun prepare(block: Config.() -> Unit): NetworkConnectivityInterceptor {
            val config = Config().apply(block)
            return NetworkConnectivityInterceptor(config.networkChecker)
        }

        override fun install(plugin: NetworkConnectivityInterceptor, scope: HttpClient) {
            scope.requestPipeline.intercept(HttpRequestPipeline.Before) {
                // Check network connectivity before proceeding with the request
                if (!plugin.networkChecker.isNetworkAvailable()) {
                    throw NoNetworkException
                }
                // If network is available, proceed with the request
                proceed()
            }
        }
    }

    /**
     * Configuration class for the network connectivity interceptor
     */
    class Config {
        lateinit var networkChecker: INetworkConnectivityChecker

        /**
         * Sets the network connectivity checker implementation
         */
        fun networkChecker(checker: INetworkConnectivityChecker) {
            this.networkChecker = checker
        }
    }
} 