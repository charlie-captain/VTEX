package com.charlie.vtex.ui.topic

import android.os.Bundle
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.charlie.vtex.base.AppTopBar
import com.charlie.vtex.ui.main.RouteKey
import com.charlie.vtex.ui.theme.VTEXTheme
import com.charlie.vtex.ui.theme.white1
import com.charlie.vtex.ui.theme.white2
import com.charlie.vtex.ui.theme.white3
import com.charlie.vtex.ui.widget.RichTextView
import com.imhanjie.v2ex.api.model.Reply
import com.imhanjie.v2ex.api.model.Topic
import com.skydoves.landscapist.glide.GlideImage

@Preview()
@Composable
fun Preview() {
    val navController = rememberNavController()
    TopicItemDetails(
        navHostController = navController,
        argument = Bundle().apply {
            putString("itemId", "794095")
        }
    )

}

@Composable
fun TopicItemDetails(navHostController: NavHostController, argument: Bundle?) {

    val topicItemId = argument?.getString("itemId")

    val viewModel: TopicDetailsViewModel = viewModel()

    val topicState = viewModel.topicLiveData.observeAsState()
    val topic = topicState.value

    if (topic == null) {
        viewModel.getTopic(topicId = topicItemId?.toLong()!!)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(VTEXTheme.colors.listItem)
    ) {
        AppTopBar(
            title = topic?.title ?: "",
            leftIcon = Icons.Default.ArrowBack,
            onLeftClick = {
                navHostController.navigateUp()
            })

        if (topic != null) {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                item {
                    TitleItem(topic)
                }

                item {
                    ReplyHeader(topic = topic)
                }

                items(topic.replies) {
                    ReplyItem(it)
                }
            }
        }


    }

}

@Composable
fun TitleItem(topic: Topic) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(Alignment.CenterVertically)
            .padding(start = 16.dp, end = 16.dp, top = 8.dp)
    ) {
        val (avatar, user, tag, title, richContent) = createRefs()
        GlideImage(
            imageModel = topic.userAvatar,
            modifier = Modifier
                .size(32.dp)
                .constrainAs(avatar) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                })

        Column(modifier = Modifier
            .padding(start = 8.dp)
            .constrainAs(user) {
                top.linkTo(avatar.top)
                bottom.linkTo(avatar.bottom)
                start.linkTo(avatar.end)
            }) {
            Text(text = topic.userName, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            Text(
                text = topic.createTime,
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 2.dp)
            )
        }

        Text(text = topic.nodeTitle, modifier = Modifier.constrainAs(tag) {
            end.linkTo(parent.end)
            top.linkTo(avatar.top)
            bottom.linkTo(avatar.bottom)
        })

        Text(
            text = topic.title,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            modifier = Modifier
                .padding(top = 6.dp)
                .constrainAs(title) {
                    top.linkTo(avatar.bottom)
                    start.linkTo(parent.start)
                }
        )

        AndroidView(factory = {
            val richTextView = RichTextView(it)
            richTextView.setRichContent(topic.richContent)
            richTextView
        }, modifier = Modifier.constrainAs(richContent) {
            top.linkTo(title.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        })

    }


}

@Composable
fun ReplyItem(reply: Reply) {

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 8.dp)
    ) {

        val (avatar, user, floor, details) = createRefs()

        GlideImage(imageModel = reply.userAvatar, modifier = Modifier
            .size(32.dp)
            .constrainAs(avatar) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
            })


        Column(modifier = Modifier
            .padding(start = 8.dp)
            .constrainAs(user) {
                top.linkTo(avatar.top)
                start.linkTo(avatar.end)
                bottom.linkTo(avatar.bottom)
            }) {
            Text(text = reply.userName, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            Text(
                text = reply.time,
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 2.dp),
                color = Color.LightGray
            )
        }

        AndroidView(factory = {
            val richTextView = RichTextView(it)
            richTextView.setRichContent(reply.content)
            richTextView
        }, modifier = Modifier
            .padding(start = 8.dp, top = 8.dp)
            .constrainAs(details) {
                top.linkTo(avatar.bottom)
                start.linkTo(avatar.end)
            })

        Text(text = "#${reply.no}", modifier = Modifier.constrainAs(floor) {
            end.linkTo(parent.end)
            top.linkTo(avatar.top)
            bottom.linkTo(avatar.bottom)
        })

    }
}

@Composable
fun ReplyHeader(topic: Topic) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(white1)
            .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
    ) {
        Text(text = "全部回复", modifier = Modifier.weight(1f))

        Text(text = "按时间顺序", Modifier.clickable {

        })
    }

}