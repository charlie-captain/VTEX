package com.imhanjie.v2ex.api.parser.impl;

import com.google.gson.Gson
import com.imhanjie.v2ex.api.ParserMatcher
import com.imhanjie.v2ex.api.model.JsonNode
import com.imhanjie.v2ex.api.parser.Parser
import com.imhanjie.v2ex.api.support.V2ex
import com.imhanjie.v2ex.api.support.parseJson
import com.imhanjie.v2ex.api.support.parseJsonList

object JsonNodeParser : ParserMatcher {


    override fun match(url: String, method: String): Boolean {
        return url == "${V2ex.BASE_URL}/api/nodes/all.json"
    }

    override fun parser(html: String): Any {
        return parseJsonList<JsonNode>(html)
    }


}
