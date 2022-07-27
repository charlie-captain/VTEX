package com.charlie.vtex.repository

import android.util.Log
import com.imhanjie.v2ex.api.model.Node
import com.imhanjie.v2ex.api.model.TopicItem

class NodeRepository : BaseRepository() {


    suspend fun loadLatestTopics(tab: String): List<TopicItem> {
        val result = api.loadLatestTopics(tab)
        return result.extract()
    }


}