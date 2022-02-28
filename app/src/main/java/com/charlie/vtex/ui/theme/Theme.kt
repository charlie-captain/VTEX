package com.charlie.vtex.ui.theme

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import com.google.accompanist.insets.ProvideWindowInsets


//设置默认主题  亮色
private val LocalVTEXColors = compositionLocalOf {
    LightColorPalette
}

private val LightColorPalette = VTEXColors(
    topBarColor = white1,
    bottomBar = white1,
    background = white1,
    listItem = white,
    divider = white3,
    chatPage = white2,
    textPrimary = black3,
    textPrimaryMe = black3,
    textSecondary = grey1,
    onBackground = grey3,
    icon = black,
    iconCurrent = green3,
    badge = red1,
    onBadge = white,
    bubbleMe = green1,
    bubbleOthers = white,
    textFieldBackground = white,
    more = grey4,
    chatPageBgAlpha = 0f,
    mainTabSelected = mainTabSelected,
    mainTabUnSelected = mainTabUnSelected,
    textBackground = textBackground,
)

private val DarkColorPalette = VTEXColors(
    topBarColor = black1,
    bottomBar = black1,
    background = black2,
    listItem = black3,
    divider = black4,
    chatPage = black2,
    textPrimary = white4,
    textPrimaryMe = black6,
    textSecondary = grey1,
    onBackground = grey1,
    icon = white5,
    iconCurrent = green3,
    badge = red1,
    onBadge = white,
    bubbleMe = green2,
    bubbleOthers = black5,
    textFieldBackground = black7,
    more = grey5,
    chatPageBgAlpha = 0f,
    mainTabSelected = mainTabSelected,
    mainTabUnSelected = mainTabUnSelected,
    textBackground = textBackgroundBlack,
)

@Stable
object VTEXTheme {
    val colors: VTEXColors
        @Composable
        get() = LocalVTEXColors.current

    enum class Theme {
        Light, Dark
    }
}


@Stable
class VTEXColors(
    topBarColor: Color,
    bottomBar: Color,
    background: Color,
    listItem: Color,
    divider: Color,
    chatPage: Color,
    textPrimary: Color,
    textPrimaryMe: Color,
    textSecondary: Color,
    onBackground: Color,
    icon: Color,
    iconCurrent: Color,
    badge: Color,
    onBadge: Color,
    bubbleMe: Color,
    bubbleOthers: Color,
    textFieldBackground: Color,
    more: Color,
    chatPageBgAlpha: Float,
    mainTabSelected: Color,
    mainTabUnSelected: Color,
    textBackground: Color,
) {

    var topBar: Color by mutableStateOf(topBarColor)
    var bottomBar: Color by mutableStateOf(bottomBar)
        private set
    var background: Color by mutableStateOf(background)
        private set
    var listItem: Color by mutableStateOf(listItem)
        private set
    var divider: Color by mutableStateOf(divider)
        private set
    var chatPage: Color by mutableStateOf(chatPage)
        private set
    var textPrimary: Color by mutableStateOf(textPrimary)
        private set
    var textPrimaryMe: Color by mutableStateOf(textPrimaryMe)
        private set
    var textSecondary: Color by mutableStateOf(textSecondary)
        private set
    var onBackground: Color by mutableStateOf(onBackground)
        private set
    var icon: Color by mutableStateOf(icon)
        private set
    var iconCurrent: Color by mutableStateOf(iconCurrent)
        private set
    var badge: Color by mutableStateOf(badge)
        private set
    var onBadge: Color by mutableStateOf(onBadge)
        private set
    var bubbleMe: Color by mutableStateOf(bubbleMe)
        private set
    var bubbleOthers: Color by mutableStateOf(bubbleOthers)
        private set
    var textFieldBackground: Color by mutableStateOf(textFieldBackground)
        private set
    var more: Color by mutableStateOf(more)
        private set
    var chatPageBgAlpha: Float by mutableStateOf(chatPageBgAlpha)
        private set
    var mainTabSelected: Color by mutableStateOf(mainTabSelected)
        private set
    var mainTabUnSelected: Color by mutableStateOf(mainTabUnSelected)
        private set
    var textBackground: Color by mutableStateOf(textBackground)
        private set

}


@Composable
fun VTEXTheme(
    theme: VTEXTheme.Theme = ThemeState.getTheme().value,
    content: @Composable () -> Unit
) {
    val targetColors = when (theme) {
        VTEXTheme.Theme.Light -> LightColorPalette
        VTEXTheme.Theme.Dark -> DarkColorPalette
    }
    val topBar = animateColorAsState(targetColors.topBar, TweenSpec(600))
    val bottomBar = animateColorAsState(targetColors.bottomBar, TweenSpec(600))
    val background = animateColorAsState(targetColors.background, TweenSpec(600))
    val listItem = animateColorAsState(targetColors.listItem, TweenSpec(600))
    val chatListDivider = animateColorAsState(targetColors.divider, TweenSpec(600))
    val chatPage = animateColorAsState(targetColors.chatPage, TweenSpec(600))
    val textPrimary = animateColorAsState(targetColors.textPrimary, TweenSpec(600))
    val textPrimaryMe = animateColorAsState(targetColors.textPrimaryMe, TweenSpec(600))
    val textSecondary = animateColorAsState(targetColors.textSecondary, TweenSpec(600))
    val onBackground = animateColorAsState(targetColors.onBackground, TweenSpec(600))
    val icon = animateColorAsState(targetColors.icon, TweenSpec(600))
    val iconCurrent = animateColorAsState(targetColors.iconCurrent, TweenSpec(600))
    val badge = animateColorAsState(targetColors.badge, TweenSpec(600))
    val onBadge = animateColorAsState(targetColors.onBadge, TweenSpec(600))
    val bubbleMe = animateColorAsState(targetColors.bubbleMe, TweenSpec(600))
    val bubbleOthers = animateColorAsState(targetColors.bubbleOthers, TweenSpec(600))
    val textFieldBackground = animateColorAsState(targetColors.textFieldBackground, TweenSpec(600))
    val more = animateColorAsState(targetColors.more, TweenSpec(600))
    val mainTabSelected = animateColorAsState(targetColors.mainTabSelected, TweenSpec(600))
    val mainTabUnSelected = animateColorAsState(targetColors.mainTabUnSelected, TweenSpec(600))
    val textBackground = animateColorAsState(targetColors.textBackground, TweenSpec(600))
    val chatPageBgAlpha = animateFloatAsState(targetColors.chatPageBgAlpha, TweenSpec(600))
    val colors = VTEXColors(
        topBarColor = topBar.value,
        bottomBar = bottomBar.value,
        background = background.value,
        listItem = listItem.value,
        divider = chatListDivider.value,
        chatPage = chatPage.value,
        textPrimary = textPrimary.value,
        textPrimaryMe = textPrimaryMe.value,
        textSecondary = textSecondary.value,
        onBackground = onBackground.value,
        icon = icon.value,
        iconCurrent = iconCurrent.value,
        badge = badge.value,
        onBadge = onBadge.value,
        bubbleMe = bubbleMe.value,
        bubbleOthers = bubbleOthers.value,
        textFieldBackground = textFieldBackground.value,
        more = more.value,
        chatPageBgAlpha = chatPageBgAlpha.value,
        mainTabSelected = mainTabSelected.value,
        mainTabUnSelected = mainTabUnSelected.value,
        textBackground = textBackground.value
    )

    CompositionLocalProvider(LocalVTEXColors provides colors) {
        MaterialTheme() {
            ProvideWindowInsets(content = content)
        }
    }
}

