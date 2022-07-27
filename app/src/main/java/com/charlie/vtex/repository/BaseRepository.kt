package com.charlie.vtex.repository

import com.charlie.vtex.api.ApiServer
import com.charlie.vtex.api.ApiService
import com.charlie.vtex.base.V2App
import com.imhanjie.v2ex.api.model.Node
import com.imhanjie.v2ex.api.model.RestfulResult
import com.imhanjie.v2ex.api.model.Topic
import com.imhanjie.v2ex.api.model.TopicItem
import java.lang.RuntimeException

open class BaseRepository {





    companion object {

        val api: ApiService = ApiServer.instance.create()

        /**
         * 错误处理
         */
        fun <T> RestfulResult<T>.extract(): T {
            if (code == RestfulResult.CODE_SUCCESS) {
                return data ?: throw RuntimeException("data can not be null!")
            } else {
                if (code == RestfulResult.CODE_USER_EXPIRED) {
                    V2App.logout()
                }
                throw RuntimeException(message!!)
            }
        }

    }

}