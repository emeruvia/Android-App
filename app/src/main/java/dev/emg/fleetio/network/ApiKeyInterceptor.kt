package dev.emg.fleetio.network

import dev.emg.fleetio.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val authenticatedRequest = original.newBuilder()
            .addHeader("Authorization", BuildConfig.AUTHORIZATION_TOKEN)
            .addHeader("Account-Token", BuildConfig.ACCOUNT_TOKEN)
            .build()
        return chain.proceed(authenticatedRequest)
    }

}