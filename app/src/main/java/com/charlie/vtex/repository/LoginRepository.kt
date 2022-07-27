package com.charlie.vtex.repository

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.charlie.vtex.api.ApiServer
import com.imhanjie.v2ex.api.model.LoginInfo
import com.imhanjie.v2ex.api.model.MyUserInfo
import com.imhanjie.v2ex.api.model.RestfulResult
import com.imhanjie.v2ex.api.model.SignInInfo
import com.imhanjie.v2ex.api.support.V2ex
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Cookie
import okhttp3.Request
import okhttp3.ResponseBody
import java.io.InputStream

class LoginRepository : BaseRepository() {


    suspend fun loadSignInInfo(): SignInInfo {
        return api.loadSignIn().extract()
    }

    suspend fun loadVerCode(verCode: String): InputStream {
        return api.loadVerImage(verCode).byteStream()
    }


    suspend fun getVerCode(cookie: String): Bitmap? {
        return withContext(Dispatchers.IO) {
            val request = Request
                .Builder()
                .addHeader(
                    "cookie", cookie
                )
                .url("${V2ex.BASE_URL}/_captcha?now=${System.currentTimeMillis()}")
                .get()
                .build()
            val response =
                ApiServer.instance.okHttpClient.newCall(request = request).execute()
            if (response.code == 200) {
                val bitmap = BitmapFactory.decodeStream(response.body?.byteStream())
                bitmap
            } else {
                null
            }
        }
    }

    suspend fun login(
        username: String,
        password: String,
        verCode: String,
        signInInfo: SignInInfo
    ): LoginInfo {
        val result = api.login(
            mapOf(
                signInInfo.keyUserName to username,
                signInInfo.keyPassword to password,
                signInInfo.keyVerCode to verCode,
                "once" to signInInfo.verUrlOnce,
                "next" to "/"
            )
        )
        return result.extract()
    }

    suspend fun loadMyUserInfo(): MyUserInfo {
        return api.loadMyUserInfo().extract()
    }

}