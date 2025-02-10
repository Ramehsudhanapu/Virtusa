package com.sipc.virtusa.utilities.network

import okhttp3.Interceptor
import okhttp3.Response

internal class HttpRequestInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val orginalRequest = chain.request()
        val request = orginalRequest.newBuilder().url(orginalRequest.url).build()
        return chain.proceed(request)

    }
}