package com.charlie.vtex.ui.topic

import android.content.res.ColorStateList
import android.graphics.Color.argb
import android.os.Bundle
import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.graphics.ColorUtils
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.charlie.vtex.base.AppTopBar
import com.charlie.vtex.ui.main.RouteKey
import com.charlie.vtex.ui.theme.*
import com.charlie.vtex.ui.widget.RichTextView
import com.imhanjie.v2ex.api.model.Reply
import com.imhanjie.v2ex.api.model.Topic
import com.skydoves.landscapist.glide.GlideImage
import java.nio.file.WatchEvent

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

    VTEXTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(VTEXTheme.colors.background)
        ) {
            AppTopBar(
                title = topic?.title ?: "",
                leftIcon = Icons.Default.ArrowBack,
                onLeftClick = {
                    navHostController.navigateUp()
                })

            if (topic != null) {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.background(VTEXTheme.colors.background)
                ) {
                    item {
                        TitleItem(topic)
                    }

                    item {
                        ReplyHeader(topic = topic)
                    }

                    items(topic.replies) {
                        ReplyItem(it)
                    }

                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                    }
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
            .background(VTEXTheme.colors.listItem)
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
                }
                .clip(CircleShape)
        )

        Column(modifier = Modifier
            .padding(start = 8.dp)
            .constrainAs(user) {
                top.linkTo(avatar.top)
                bottom.linkTo(avatar.bottom)
                start.linkTo(avatar.end)
            }) {
            Text(
                text = topic.userName,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = VTEXTheme.colors.textPrimary
            )
            Text(
                text = topic.createTime,
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 2.dp),
                color = VTEXTheme.colors.textPrimary
            )
        }

        Text(
            text = topic.nodeTitle,
            modifier = Modifier
                .constrainAs(tag) {
                    end.linkTo(parent.end)
                    top.linkTo(avatar.top)
                    bottom.linkTo(avatar.bottom)
                }
                .clip(RoundedCornerShape(8.dp))
                .background(VTEXTheme.colors.textBackground)
                .padding(start = 4.dp, end = 4.dp, top = 1.dp, bottom = 1.dp),
            color = VTEXTheme.colors.textSecondary,
            fontSize = 13.sp
        )

        Text(
            text = topic.title,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = VTEXTheme.colors.textPrimary,
            modifier = Modifier
                .padding(top = 6.dp)
                .constrainAs(title) {
                    top.linkTo(avatar.bottom)
                    start.linkTo(parent.start)
                }
        )
        val textColor = VTEXTheme.colors.textPrimary

        AndroidView(factory = {
            val richTextView = RichTextView(it)
            richTextView.setRichContent(topic.richContent)
            richTextView.setTextColor(
                argb(
                    textColor.alpha,
                    textColor.red,
                    textColor.green,
                    textColor.blue
                )
            )
            richTextView
        }, modifier = Modifier
            .constrainAs(richContent) {
                top.linkTo(title.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
            .padding(top = 8.dp, bottom = 8.dp))

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
            .clip(CircleShape)
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
            Text(
                text = reply.userName,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = VTEXTheme.colors.textPrimary
            )
            Text(
                text = reply.time,
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 2.dp),
                color = VTEXTheme.colors.textSecondary
            )
        }

        val textColor = VTEXTheme.colors.textPrimary

        AndroidView(factory = {
            val richTextView = RichTextView(it)
            richTextView.setRichContent(reply.content)
            richTextView.setTextColor(
                android.graphics.Color.argb(
                    textColor.alpha,
                    textColor.red,
                    textColor.green,
                    textColor.blue
                )
            )
            richTextView
        }, modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, top = 8.dp, bottom = 8.dp)
            .constrainAs(details) {
                top.linkTo(avatar.bottom)
                start.linkTo(avatar.end)
            })

        Text(
            text = "#${reply.no}", modifier = Modifier.constrainAs(floor) {
                end.linkTo(parent.end)
                top.linkTo(avatar.top)
                bottom.linkTo(avatar.bottom)
            },
            color = VTEXTheme.colors.textPrimary
        )

    }
}

@Composable
fun ReplyHeader(topic: Topic) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(VTEXTheme.colors.textBackground)
            .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
    ) {
        Text(
            text = "全部回复",
            modifier = Modifier.weight(1f),
            fontSize = 14.sp,
            color = VTEXTheme.colors.textPrimary
        )

        Text(
            text = "按时间顺序", Modifier.clickable {

            }, fontSize = 14.sp,
            color = VTEXTheme.colors.textPrimary
        )
    }

}