package com.charlie.vtex.base

import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.charlie.vtex.ui.theme.VTEXTheme

@Composable
fun AppTopBar(
    title: String,
    leftIcon: ImageVector? = null,
    rightIcon: ImageVector? = null,
    onLeftClick: (() -> Unit)? = null,
    onRightClick: (() -> Unit)? = null,
) {

    Row(
        modifier = Modifier
            .height(46.dp)
            .background(VTEXTheme.colors.topBar),
        verticalAlignment = Alignment.CenterVertically
    ) {

        if (leftIcon != null) {
            Icon(
                leftIcon,
                null,
                modifier = Modifier
                    .padding(start = 16.dp)
                    .clickable(
                        onClick = { onLeftClick?.invoke() },
                    ),
                tint = VTEXTheme.colors.icon
            )
        } else {
            Spacer(
                modifier = Modifier
                    .size(24.dp)
                    .padding(start = 16.dp)
            )
        }

        Text(
            text = title,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp, end = 16.dp),
            fontSize = 16.sp,
            color = VTEXTheme.colors.textPrimary,
            maxLines = 1,
            fontWeight = FontWeight.Medium
        )

        if (rightIcon != null) {
            Icon(
                rightIcon, null, modifier = Modifier
                    .padding(end = 16.dp)
                    .clickable {
                        onRightClick?.invoke()
                    }, tint = VTEXTheme.colors.icon
            )
        } else {
            Spacer(
                modifier = Modifier
                    .size(24.dp)
                    .padding(start = 16.dp)
            )
        }

    }

}