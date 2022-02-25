package com.charlie.vtex.ui.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.charlie.vtex.base.BaseViewModel
import com.charlie.vtex.repository.AllNodeRepository
import com.charlie.vtex.repository.NodeRepository
import com.elvishew.xlog.XLog
import com.imhanjie.v2ex.api.model.TopicItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.Exception

class HomeViewModel() : BaseViewModel() {

    override val repository: NodeRepository = NodeRepository()

    val homeTabListMap = hashMapOf<String, MutableLiveData<List<TopicItem>>>()

    val _isRefreshing: MutableLiveData<Boolean> = MutableLiveData(false)

    var loadTabJobs: MutableSet<String> = mutableSetOf()

    fun getTab(tab: String) {
        if (loadTabJobs.contains(tab)) {
            return
        }
        loadTabJobs.add(tab)
        viewModelScope.launch {
            try {
                loadTabJobs.add(tab)
                XLog.d("asdqwe", "start load $tab  ")
                delay(1000)
                val data = repository.loadLatestTopics(tab)
//                val data = mutableListOf<TopicItem>()
//                repeat(100) {
//                    data.add(TopicItem(1, tab.title, "个港股", "覆盖", "发送给梵蒂冈", "", "2 小时前", 32L, false))
//                }
                XLog.d("asdqwe", "write tab =$tab , data = ${data.size}")
                homeTabListMap[tab]?.value = data
            } catch (e: Exception) {
                XLog.e(e)
            } finally {
                _isRefreshing.value = false
                loadTabJobs.remove(tab)
            }
        }
    }


}