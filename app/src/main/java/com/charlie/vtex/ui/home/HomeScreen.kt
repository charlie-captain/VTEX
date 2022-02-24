package com.charlie.vtex.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.charlie.vtex.ui.main.RouteKey
import com.charlie.vtex.ui.theme.VTEXTheme
import com.google.accompanist.pager.*
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.imhanjie.v2ex.api.model.TopicItem
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HomeScreen(navHostController: NavHostController) {
    val titles = TopicTab.values()
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()

    val viewModel: HomeViewModel = viewModel()

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        ScrollableTabRow(
            modifier = Modifier.fillMaxWidth(),
            selectedTabIndex = pagerState.currentPage,
            edgePadding = 0.dp,
            backgroundColor = VTEXTheme.colors.topBar,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
                )
            },
        ) {
            titles.forEachIndexed { index, s ->
                Tab(
                    text = { Text(text = s.title) },
                    selected = pagerState.currentPage == index,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    })
            }
        }
        ViewPager(navHostController, titles = titles, state = pagerState, viewModel)
    }
}


@ExperimentalPagerApi
@Composable
fun ViewPager(
    navHostController: NavHostController,
    titles: Array<TopicTab>,
    state: PagerState,
    viewModel: HomeViewModel,
) {

    HorizontalPager(state = state, count = titles.size, modifier = Modifier.fillMaxWidth()) {

        val topicTab = titles[currentPage].value
        val data = viewModel.homeTabListMap[topicTab]
        if (data == null) {
            viewModel.homeTabListMap[topicTab] =
                MutableLiveData(emptyList())
        }
        val stateData =
            viewModel.homeTabListMap[topicTab]?.observeAsState()
                ?: return@HorizontalPager
        val list = stateData.value ?: return@HorizontalPager
        if (list.isEmpty()) {
            viewModel.getTab(topicTab)
        }

        val isRefreshing by viewModel._isRefreshing.observeAsState(false)

        TabFragment(navHostController, list, isRefreshing) {
            //将刷新状态的值改为true  显示加载动画
            viewModel._isRefreshing.value = true
            //重新请求数据
            viewModel.getTab(topicTab)
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabFragment(
    navHostController: NavHostController,
    list: List<TopicItem>,
    isRefreshing: Boolean,
    onRefresh: () -> Unit
) {
    SwipeRefresh(state = rememberSwipeRefreshState(isRefreshing = isRefreshing),
        onRefresh = {
            onRefresh.invoke()
        }) {
        RecyclerView(
            navHostController,
            list
        )
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun RecyclerView(
    navHostController: NavHostController,
    list: List<TopicItem>
) {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items(list) { item ->
            List(navHostController = navHostController, item = item)
        }
    }

}

@Composable
fun List(navHostController: NavHostController, item: TopicItem) {
    val paddingStart = 16.dp
    val paddingEnd = 16.dp
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(VTEXTheme.colors.listItem)
            .clickable {
                navHostController.navigate(
                    "${RouteKey.TopicItemDetails.route}/${item.id}",
                )
            },
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = paddingStart, end = paddingEnd, top = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            GlideImage(
                imageModel = item.userAvatar,
                modifier = Modifier.size(28.dp),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = item.userName,
                        color = Color.Black,
                        fontSize = 13.sp,
                    )
                    Text(
                        text = item.nodeTitle,
                        modifier = Modifier
                            .padding(start = 4.dp)
                            .clip(
                                RoundedCornerShape(16.dp)
                            )
                            .background(Color.LightGray)
                            .padding(start = 6.dp, end = 6.dp, top = 1.dp, bottom = 1.dp),
                        fontSize = 12.sp,
                        color = Color.White
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = item.replies.toString(),
                        color = Color.White,
                        fontSize = 10.sp,
                        modifier = Modifier
                            .clip(
                                RoundedCornerShape(16.dp)
                            )
                            .background(Color.LightGray)
                            .padding(start = 6.dp, end = 6.dp, top = 1.dp, bottom = 1.dp)
                    )
                    Text(
                        text = item.latestReplyTime,
                        color = Color.LightGray,
                        modifier = Modifier.padding(start = 4.dp),
                        fontSize = 12.sp
                    )
                }
            }
        }
        Text(
            text = item.title,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = paddingStart, end = paddingEnd, top = 8.dp),
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
                .height(2.dp)
                .background(VTEXTheme.colors.divider)
        )
    }
}