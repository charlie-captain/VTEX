package com.charlie.vtex.ui.login

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.charlie.vtex.base.AppTopBar
import com.skydoves.landscapist.glide.GlideImage


@Composable
fun LoginPage(navHostController: NavHostController) {

    val loginViewModel: LoginViewModel = viewModel()

    val username = remember {
        mutableStateOf("")
    }

    val password = remember {
        mutableStateOf("")
    }

    val verificationCode = remember {
        mutableStateOf("")
    }

    val verCodeBitmap = loginViewModel.verCodeBitmap.observeAsState()

    val loginResult = loginViewModel.loginResult.observeAsState()
    val isLogin = loginViewModel.isLoginState.observeAsState()
    val loginError = loginViewModel.loginError.observeAsState()

    loginError.value?.let {
        Toast.makeText(LocalContext.current, it, Toast.LENGTH_SHORT).show()
        loginViewModel.loginError.value = null
    }

    loginResult.value?.let {
        Toast.makeText(LocalContext.current, "登录成功", Toast.LENGTH_SHORT).show()
        navHostController.navigateUp()
    }

    Column {
        AppTopBar(title = "Login", leftIcon = Icons.Filled.ArrowBack, onLeftClick = {
            navHostController.navigateUp()
        })

        Text(
            text = "V2EX",
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            textAlign = TextAlign.Center,
            fontSize = 24.sp
        )

        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(start = 32.dp, end = 32.dp, top = 16.dp)
            ) {

                OutlinedTextField(
                    value = username.value,
                    modifier = Modifier.fillMaxWidth(),
                    onValueChange = {
                        username.value = it
                    },
                    label = { Text(text = "Username") },
                )

                OutlinedTextField(
                    value = password.value,
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .fillMaxWidth(),
                    onValueChange = {
                        password.value = it
                    },
                    label = { Text(text = "Password") },
                )

                GlideImage(
                    imageModel = verCodeBitmap.value,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .padding(top = 8.dp)
                        .clickable {
                            loginViewModel.loadSignInfo()
                        },
                    contentScale = ContentScale.Fit,
                    placeHolder = Icons.Filled.Image
                )

                OutlinedTextField(
                    value = verificationCode.value,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    onValueChange = {
                        verificationCode.value = it
                    },
                    label = { Text(text = "Verification Code") },
                )

                if (isLogin.value == true) {
                    CircularProgressIndicator(modifier = Modifier.padding(top = 8.dp))
                } else {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        onClick = {
                            loginViewModel.login(
                                username = username.value,
                                password = password.value,
                                vercode = verificationCode.value
                            )
                        }) {
                        Text(text = "Login")
                    }
                }
            }
        }

    }


}