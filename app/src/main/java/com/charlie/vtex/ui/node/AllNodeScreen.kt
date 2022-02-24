package com.charlie.vtex.ui.node

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.charlie.vtex.ui.main.RouteKey
import com.charlie.vtex.ui.theme.VTEXTheme
import com.imhanjie.v2ex.api.model.JsonNode

@Composable
fun AllNodeScreen(navHostController: NavHostController) {

    val viewModel: AllNodeViewModel = viewModel()

    val allNodeMap = remember { viewModel.allNodeMap }

    val currentParent = remember { mutableStateOf<String?>(null) }

    Row {
        ListLeft(allNodeMap, currentParent)
        Spacer(
            modifier = Modifier
                .width(4.dp)
                .fillMaxHeight()
                .background(VTEXTheme.colors.divider)
        )
        ListRight(allNodeMap, currentParent, navHostController)
    }
}

@Composable
fun ListLeft(
    allNode: SnapshotStateMap<String, List<JsonNode>>, currentParent: MutableState<String?>
) {
    LazyColumn(
        modifier = Modifier
            .width(100.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val toList = allNode.keys.toList()
        items(toList.count()) { index ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        currentParent.value = toList[index]
                    }, contentAlignment = Alignment.Center
            ) {
                Text(
                    modifier = Modifier.padding(
                        start =
                        16.dp, end = 16.dp, top = 4.dp, bottom = 4.dp
                    ),
                    text = toList[index],
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}


@Composable
fun ListRight(
    allNode: SnapshotStateMap<String, List<JsonNode>>,
    currentParent: MutableState<String?>,
    navHostController: NavHostController
) {
    val key: String? =
        if (currentParent.value == null) allNode.keys.toList()
            .getOrNull(0) else currentParent.value
    if (key == null) return
    val nodes = allNode.get(key) ?: return
    LazyColumn {
        items(nodes.size) { index ->
            nodes.getOrNull(index)?.let {
                Box(
                    contentAlignment = Alignment.CenterStart,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            navHostController.navigate("${RouteKey.Tab}/${it.name}")
                        }
                ) {
                    Text(
                        text = it.title ?: "",
                        fontSize = 16.sp,
                        modifier = Modifier.padding(
                            start = 16.dp,
                            end = 16.dp,
                            top = 6.dp,
                            bottom = 6.dp
                        )
                    )
                }
            }
        }
    }
}
