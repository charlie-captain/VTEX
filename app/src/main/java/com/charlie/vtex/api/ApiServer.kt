package com.charlie.vtex.api

import com.imhanjie.v2ex.api.CookieInterceptor
import com.imhanjie.v2ex.api.HeaderInterceptor
import com.imhanjie.v2ex.api.LoginInterceptor
import com.imhanjie.v2ex.api.ParserInterceptor
import com.imhanjie.v2ex.api.support.V2ex.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class ApiServer {

    var okHttpClient: OkHttpClient
        private set

    init {
        val builder = OkHttpClient.Builder()
        builder
            .followRedirects(false) // 禁用 302 重定向
            .followSslRedirects(false)    // 禁用 302 重定向
            .addInterceptor(HeaderInterceptor())
            .addInterceptor(LoginInterceptor())
            .addInterceptor(ParserInterceptor())
            .addInterceptor(CookieInterceptor)
        okHttpClient = builder.build()
    }


    inline fun <reified T> create(): T {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("$BASE_URL/")
            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
            .create(T::class.java)
    }


    companion object {

        val instance: ApiServer by lazy {
            ApiServer()
        }


    }
}