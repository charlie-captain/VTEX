package com.charlie.vtex.repository

import com.imhanjie.v2ex.api.model.JsonNode

class AllNodeRepository: BaseRepository() {



    suspend fun loadAllNode(): List<JsonNode> {
        return api.loadAllNode().extract()
    }
}