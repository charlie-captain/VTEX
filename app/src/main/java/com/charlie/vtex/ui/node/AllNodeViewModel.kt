package com.charlie.vtex.ui.node

import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.viewModelScope
import com.charlie.vtex.base.BaseViewModel
import com.charlie.vtex.repository.AllNodeRepository
import com.elvishew.xlog.XLog
import com.imhanjie.v2ex.api.model.JsonNode
import kotlinx.coroutines.launch

class AllNodeViewModel : BaseViewModel() {


    override val repository: AllNodeRepository = AllNodeRepository()

    val allNodeMap = mutableStateMapOf<String, List<JsonNode>>()

    init {
        loadAllNode()
    }

    private fun loadAllNode() {
        viewModelScope.launch {
            try {
                val allNode = repository.loadAllNode()

                allNode.forEach {
                    val nodeParentName = it.parentNodeName
                    val key = if (nodeParentName.isNullOrEmpty()) {
                        "other"
                    } else {
                        nodeParentName
                    }
                    val list = allNodeMap[key]?.toMutableList() ?: mutableListOf()
                    list.add(it)
                    allNodeMap[key] = list
                }

                XLog.d("loadAllNode, all.size= ${allNode.size}")
            } catch (e: Exception) {
                XLog.e(e)
            }
        }

    }

}