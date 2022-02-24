package com.charlie.vtex.repository

import android.util.Log
import com.imhanjie.v2ex.api.model.Topic
import com.imhanjie.v2ex.api.model.TopicItem

class TopicItemDetailsRepository : BaseRepository() {


    suspend fun loadTopic(topicId: Long, page: Int): Topic {
        val result = api.loadTopic(topicId, page)
        return result.extract()
    }


}