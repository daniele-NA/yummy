package me.app.yummy.compose.widgets.animations

import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import me.app.yummy.compose.theme.TWEEN_DURATION

@Composable
fun TopAnimated(visible: Boolean, content: @Composable () -> Unit) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(tween(TWEEN_DURATION)) +
                slideInVertically(
                    initialOffsetY = { -it },
                    animationSpec = tween(TWEEN_DURATION, easing = FastOutSlowInEasing)
                ),
        exit = fadeOut(tween(TWEEN_DURATION)) +
                slideOutVertically(
                    targetOffsetY = { -it },
                    animationSpec = tween(TWEEN_DURATION, easing = FastOutSlowInEasing)
                )
    ) { content() }
}

@Composable
fun BottomAnimated(visible: Boolean, content: @Composable () -> Unit) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(tween(TWEEN_DURATION)) +
                slideInVertically(
                    initialOffsetY = { it },
                    animationSpec = tween(TWEEN_DURATION, easing = FastOutSlowInEasing)
                ),
        exit = fadeOut(tween(TWEEN_DURATION)) +
                slideOutVertically(
                    targetOffsetY = { it },
                    animationSpec = tween(TWEEN_DURATION, easing = FastOutSlowInEasing)
                )
    ) { content() }
}

@Composable
fun LeftAnimated(visible: Boolean, content: @Composable () -> Unit) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(tween(TWEEN_DURATION)) +
                slideInHorizontally(
                    initialOffsetX = { -it },
                    animationSpec = tween(TWEEN_DURATION, easing = FastOutSlowInEasing)
                ),
        exit = fadeOut(tween(TWEEN_DURATION)) +
                slideOutHorizontally(
                    targetOffsetX = { -it },
                    animationSpec = tween(TWEEN_DURATION, easing = FastOutSlowInEasing)
                )
    ) { content() }
}

@Composable
fun RightAnimated(visible: Boolean, content: @Composable () -> Unit) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(tween(TWEEN_DURATION)) +
                slideInHorizontally(
                    initialOffsetX = { it },
                    animationSpec = tween(TWEEN_DURATION, easing = FastOutSlowInEasing)
                ),
        exit = fadeOut(tween(TWEEN_DURATION)) +
                slideOutHorizontally(
                    targetOffsetX = { it },
                    animationSpec = tween(TWEEN_DURATION, easing = FastOutSlowInEasing)
                )
    ) { content() }
}
