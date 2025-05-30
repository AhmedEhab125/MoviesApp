package com.example.network.di

import com.example.network.NetworkHelper
import com.example.network.connectivity.INetworkConnectivityChecker
import com.example.network.connectivity.NetworkConnectivityChecker
import com.example.network.networkClient.provideOkHttpClient
import io.ktor.client.HttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val CoreNetworkModule = module {
    single<INetworkConnectivityChecker> {
        NetworkConnectivityChecker(androidContext())
    }

    single<HttpClient> {
        provideOkHttpClient(get<INetworkConnectivityChecker>())
    }
    
    single<NetworkHelper> { NetworkHelper() }
}