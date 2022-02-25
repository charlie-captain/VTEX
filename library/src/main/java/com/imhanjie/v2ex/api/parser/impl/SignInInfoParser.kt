package com.imhanjie.v2ex.api.parser.impl

import android.annotation.SuppressLint
import com.imhanjie.v2ex.api.ParserMatcher
import com.imhanjie.v2ex.api.model.SignInInfo
import com.imhanjie.v2ex.api.support.V2ex
import okhttp3.Response
import okhttp3.ResponseBody
import org.jsoup.Jsoup

object SignInInfoParser : ParserMatcher {

    @SuppressLint("DefaultLocale")
    override fun match(url: String, method: String): Boolean {
        return url == "${V2ex.BASE_URL}/signin" && method.toUpperCase() == "GET"
    }

    override fun parser(html: String): Any {
        return Unit
    }

    fun parser(response: Response, html: String): SignInInfo {
        val cookie = response.headers("set-cookie")[0]
        val document = Jsoup.parse(html)
        val eTrs = document.selectFirst("#Main").selectFirst("table").select("tr")
        val keyUserName = eTrs[0].selectFirst("input").attr("name")
        val keyPassword = eTrs[1].selectFirst("input").attr("name")
        val keyVerCode = eTrs[2].selectFirst("input").attr("name")
        val verUrlOnce = eTrs[3].selectFirst("input").attr("value")

        return SignInInfo(
            keyUserName,
            keyPassword,
            keyVerCode,
            verUrlOnce,
            cookie
        )
    }

}