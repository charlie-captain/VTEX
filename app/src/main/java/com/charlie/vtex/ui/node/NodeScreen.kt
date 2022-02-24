package com.charlie.vtex.ui.node

import android.os.Bundle
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.charlie.vtex.base.AppTopBar
import com.charlie.vtex.ui.home.HomeViewModel
import com.charlie.vtex.ui.home.TabFragment

@Composable
fun NodeScreen(navHostController: NavHostController, arguments: Bundle?) {

    val tab = arguments?.getString("tab") ?: return

    val nodeViewModel: NodeViewModel = viewModel(factory = NodeViewModelFactory(tab))

    val topicList = remember { nodeViewModel.topicList }
    val isRefreshing by nodeViewModel.isRefreshing.observeAsState(false)


    Column {
        AppTopBar(
            title = topicList.firstOrNull()?.nodeTitle ?: "",
            leftIcon = Icons.Default.ArrowBack,
            onLeftClick = {
                navHostController.navigateUp()
            })
        TabFragment(
            navHostController = navHostController,
            topicList,
            isRefreshing,
        ) {
            nodeViewModel.isRefreshing.value = false
        }
    }

}