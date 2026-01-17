package me.app.yummy.compose.widgets.animations

import yummy.composeapp.generated.resources.Res
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import io.github.alexzhirkevich.compottie.LottieCompositionSpec
import io.github.alexzhirkevich.compottie.animateLottieCompositionAsState
import io.github.alexzhirkevich.compottie.rememberLottieComposition
import io.github.alexzhirkevich.compottie.rememberLottiePainter

@Composable
fun LottieAnimation(modifier: Modifier, path: String, iterations: Int=Int.MAX_VALUE) {
    val composition by rememberLottieComposition {
        LottieCompositionSpec.JsonString(
            Res.readBytes(path).decodeToString()
        )
    }
    val progress by animateLottieCompositionAsState(composition,iterations=iterations)

    Image(
        modifier=modifier,
        painter = rememberLottiePainter(
            composition = composition,
            progress = { progress },
        ),
        contentDescription =null
    )
}