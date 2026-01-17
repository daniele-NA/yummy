package me.app.yummy.compose.widgets.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import me.app.yummy.compose.theme.buttonBrush

@Composable
fun GradientButton(modifier: Modifier = Modifier, text: String, onClick: () -> Unit) {


    // fixme : MAKE SURE TO HANDLE PROPERLY THE RESIZE FOR LARGE SCREEN == //
    Box {
        // Gradiente sotto
        Box(
            modifier = Modifier
                .height(61.dp)
                .width(180.dp)
                .background(
                    Brush.verticalGradient(
                        colors = buttonBrush
                    ),
                    shape = RoundedCornerShape(55.dp)
                )
        )

        AppButton(
            modifier = modifier.width(200.dp).align(Alignment.Center),
            text = text,
            onClick = onClick
        )
    }

}