package com.charlie.vtex.ui.theme

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.charlie.vtex.base.SpConstants
import com.tencent.mmkv.MMKV

object ThemeState {

    private var state: MutableState<VTEXTheme.Theme> = mutableStateOf(VTEXTheme.Theme.Light)

    fun initTheme() {
        val themeName = MMKV.defaultMMKV().decodeString(SpConstants.UI_MODE)
        val theme = VTEXTheme.Theme.values().firstOrNull { it.name == themeName }
        if (theme != null) {
            state.value = theme
        }
    }

    fun changeTheme() {
        val values = VTEXTheme.Theme.values()
        val currentTheme = values.indexOf(state.value)

        val nextTheme =
            if (currentTheme != -1) values[(currentTheme + 1) % values.size] else values.first()

        state.value = nextTheme
        MMKV.defaultMMKV().encode(SpConstants.UI_MODE, nextTheme.name)
    }

    fun getTheme(): MutableState<VTEXTheme.Theme> {
        return state
    }

}