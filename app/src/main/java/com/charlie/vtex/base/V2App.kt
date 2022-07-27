package com.charlie.vtex.base

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.elvishew.xlog.LogLevel
import com.elvishew.xlog.XLog
import com.imhanjie.v2ex.api.*
import com.tencent.mmkv.MMKV
import okhttp3.OkHttpClient

class V2App : Application() {

    override fun onCreate() {
        super.onCreate()

        context = this
        MMKV.initialize(this)

        XLog.init(LogLevel.ALL);

        V2exApi.init {
            AppSession.getUserInfo().value!!.a2Cookie
        }


    }

    companion object {

        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context

        fun logout(){
            AppSession.clear()
        }
    }
}