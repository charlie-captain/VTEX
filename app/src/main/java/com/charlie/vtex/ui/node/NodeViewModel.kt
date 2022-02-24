package com.charlie.vtex.ui.node

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.charlie.vtex.base.BaseViewModel
import com.charlie.vtex.repository.NodeRepository
import com.imhanjie.v2ex.api.model.TopicItem
import kotlinx.coroutines.launch

class NodeViewModel(private val tab: String) : BaseViewModel() {

    override val repository: NodeRepository = NodeRepository()

    val isRefreshing: MutableLiveData<Boolean> = MutableLiveData(false)

    val topicList = mutableStateListOf<TopicItem>()


    init {
        viewModelScope.launch {
            topicList.addAll(repository.loadLatestTopics(tab))
        }

    }
}

class NodeViewModelFactory(private val tab: String) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NodeViewModel(tab) as T
    }
}