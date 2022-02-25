package com.charlie.vtex.ui.login

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.charlie.vtex.api.ApiServer
import com.charlie.vtex.base.AppSession
import com.charlie.vtex.base.BaseViewModel
import com.charlie.vtex.repository.LoginRepository
import com.elvishew.xlog.XLog
import com.imhanjie.v2ex.api.model.LoginInfo
import com.imhanjie.v2ex.api.model.SignInInfo
import com.imhanjie.v2ex.api.support.V2ex.BASE_URL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Request
import java.io.InputStream
import kotlin.math.sign

class LoginViewModel : BaseViewModel() {

    override val repository: LoginRepository = LoginRepository()

    val signInfo = MutableLiveData<SignInInfo>(null)

    val verCodeBitmap = MutableLiveData<Bitmap>(null)

    val isLoginState = MutableLiveData<Boolean>(false)

    val loginResult = MutableLiveData<LoginInfo>(null)

    val loginError = MutableLiveData<String>(null)

    init {
        loadSignInfo()
    }

    fun loadSignInfo() {
        viewModelScope.launch {
            try {
                XLog.d("start load sign info")
                signInfo.value = repository.loadSignInInfo()
                val cookie = signInfo.value!!.cookie
                if (cookie != null) {
                    repository.getVerCode(cookie)?.let {
                        clearBitmap()
                        verCodeBitmap.postValue(it)
                    }
                }
                XLog.d("login $signInfo,")
            } catch (e: Exception) {
                XLog.e("loadSignInfo", e)
            }
        }
    }

    private fun clearBitmap() {
        if (verCodeBitmap.value != null && verCodeBitmap.value?.isRecycled == false) {
            verCodeBitmap.value?.recycle()
        }
    }


    fun login(username: String, password: String, vercode: String) {
        if (username.isNullOrEmpty() || password.isNullOrEmpty() || vercode.isNullOrEmpty() || signInfo.value == null) {
            return
        }

        viewModelScope.launch {
            try {
                isLoginState.value = true
                val loginInfo =
                    repository.login(username, password, vercode, signInInfo = signInfo.value!!)


                // 2. 存储用户标识 cookie
                AppSession.setOrUpdateUserInfo(
                    AppSession.getUserInfo().value!!.copy(
                        a2Cookie = loginInfo.cookie
                    )
                )
                // 3. 获取用户信息
                val myUserInfo = repository.loadMyUserInfo()
                AppSession.setOrUpdateUserInfo(myUserInfo)

                XLog.d(loginInfo.cookie)
                loginResult.value = loginInfo
            } catch (e: Exception) {
                loginError.value = e.message
                loadSignInfo()
                XLog.e(e)
            } finally {
                isLoginState.value = false
            }
        }
    }

    override fun onCleared() {
        super.onCleared()

        clearBitmap()
    }
}