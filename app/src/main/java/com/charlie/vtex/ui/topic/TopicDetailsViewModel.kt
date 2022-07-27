package com.charlie.vtex.ui.topic

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.charlie.vtex.base.BaseViewModel
import com.charlie.vtex.repository.BaseRepository
import com.charlie.vtex.repository.TopicItemDetailsRepository
import com.elvishew.xlog.XLog
import com.imhanjie.v2ex.api.model.Reply
import com.imhanjie.v2ex.api.model.Topic
import kotlinx.coroutines.launch

class TopicDetailsViewModel : BaseViewModel() {

    val topicLiveData = MutableLiveData<Topic>(null)

    var isLoadingTopic = false

    override val repository: TopicItemDetailsRepository = TopicItemDetailsRepository()

    fun getTopic(topicId: Long) {
        if (isLoadingTopic) {
            return
        }
        viewModelScope.launch {
            try {
                if (isLoadingTopic) {
                    return@launch
                }
                isLoadingTopic = true
                val topic = repository.loadTopic(topicId = topicId, 0)
                topicLiveData.value = topic
            } catch (e: Exception) {
                XLog.e(e)
            } finally {
                isLoadingTopic = false
            }
        }
    }

}