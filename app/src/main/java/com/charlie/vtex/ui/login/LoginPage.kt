package com.charlie.vtex.ui.login

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.charlie.vtex.base.AppTopBar
import com.charlie.vtex.ui.theme.VTEXTheme
import com.skydoves.landscapist.glide.GlideImage


@OptIn(ExperimentalComposeUiApi::class)
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

    val keyboard = LocalSoftwareKeyboardController.current
    val scrollState = rememberScrollState()
    VTEXTheme {

        Column {
            AppTopBar(title = "Login", leftIcon = Icons.Filled.ArrowBack, onLeftClick = {
                navHostController.navigateUp()
            })

            Text(
                text = "V2EX",
                modifier = Modifier
                    .fillMaxWidth()
                    .background(VTEXTheme.colors.background)
                    .padding(top = 16.dp),
                textAlign = TextAlign.Center,
                fontSize = 24.sp, color = VTEXTheme.colors.textPrimary
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(VTEXTheme.colors.background),
                contentAlignment = Alignment.TopCenter
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .scrollable(scrollState, orientation = Orientation.Vertical)
                        .padding(start = 32.dp, end = 32.dp, top = 8.dp)
                ) {

                    OutlinedTextField(
                        value = username.value,
                        modifier = Modifier.fillMaxWidth(),
                        onValueChange = {
                            username.value = it
                        },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            backgroundColor = VTEXTheme.colors.textFieldBackground,
                            textColor = VTEXTheme.colors.textPrimary,
                            disabledLabelColor = VTEXTheme.colors.textSecondary,
                            disabledTextColor =  VTEXTheme.colors.textSecondary
                        ),
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
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next,
                            keyboardType = KeyboardType.Password
                        ),

                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            backgroundColor = VTEXTheme.colors.textFieldBackground,
                            textColor = VTEXTheme.colors.textPrimary,
                            disabledLabelColor = VTEXTheme.colors.textSecondary,
                            disabledTextColor =  VTEXTheme.colors.textSecondary
                        ),
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
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(onDone = {
                            keyboard?.hide()
                            loginViewModel.login(
                                username = username.value,
                                password = password.value,
                                vercode = verificationCode.value
                            )
                        }),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            backgroundColor = VTEXTheme.colors.textFieldBackground,
                            textColor = VTEXTheme.colors.textPrimary,
                            disabledLabelColor = VTEXTheme.colors.textSecondary,
                            disabledTextColor =  VTEXTheme.colors.textSecondary
                        ),
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
                                keyboard?.hide()
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


}