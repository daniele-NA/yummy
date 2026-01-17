package me.app.yummy.compose.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CardBlur(modifier: Modifier = Modifier,content: @Composable ()->Unit){
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
    ) {

        // BLUR LAYER //
        Box(
            modifier = Modifier
                .matchParentSize()
                .blur(40.dp)
        )

        // LIGHT LAYER //
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(Color.White.copy(alpha = 0.6f))
        )

        // SIMPLE CONTENT //
        Column {
            content()
        }
    }
}