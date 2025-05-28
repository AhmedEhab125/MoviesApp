package com.example.network.di

import com.example.network.NetworkHelper
import com.example.network.networkClient.provideOkHttpClient
import io.ktor.client.HttpClient
import org.koin.dsl.module

val CoreNetworkModule = module {
    single<HttpClient> { provideOkHttpClient() }
    single<NetworkHelper> { NetworkHelper() }
}