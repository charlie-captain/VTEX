package com.charlie.vtex.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.charlie.vtex.base.AppSession
import com.charlie.vtex.ui.main.RouteKey
import com.charlie.vtex.ui.theme.VTEXTheme
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun ProfilePage(navHostController: NavHostController) {

    val profileViewModel: ProfileViewModel = viewModel()
    val userInfoState = AppSession.getUserInfo().observeAsState()
    val userLoginState = AppSession.getLoginState().observeAsState()

    val userInfo = userInfoState.value

    if (userLoginState.value == false) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(VTEXTheme.colors.background), contentAlignment = Alignment.Center
        ) {
            Button(
                onClick = {
                    navHostController.navigate(RouteKey.Login.route)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 32.dp, end = 32.dp)
                    .align(Alignment.Center),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.LightGray,
                )
            ) {
                Text(text = "Login")
            }
        }
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(VTEXTheme.colors.background)
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(Alignment.CenterVertically)
                .padding(start = 16.dp, end = 16.dp, top = 8.dp)
        ) {
            val (avatar, user, tag, title, richContent) = createRefs()
            GlideImage(
                imageModel = userInfo?.userAvatar,
                modifier = Modifier
                    .size(48.dp)
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
                    text = userInfo?.userName ?: "",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = VTEXTheme.colors.textPrimary
                )
            }

        }

    }
}