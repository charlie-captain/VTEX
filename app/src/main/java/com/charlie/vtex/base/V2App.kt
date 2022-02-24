package com.charlie.vtex.base

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.elvishew.xlog.LogLevel
import com.elvishew.xlog.XLog
import com.imhanjie.v2ex.api.*
import okhttp3.OkHttpClient

class V2App : Application() {

    override fun onCreate() {
        super.onCreate()

        context = this

        XLog.init(LogLevel.ALL);


        V2exApi.init {
            ""
        }


    }

    companion object {

        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context

    }
}