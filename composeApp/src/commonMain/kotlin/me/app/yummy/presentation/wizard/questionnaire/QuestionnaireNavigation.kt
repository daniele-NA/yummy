package me.app.yummy.presentation.wizard.questionnaire

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import me.app.yummy.compose.theme.LATERAL_PADDING
import me.app.yummy.compose.theme.textBrush
import me.app.yummy.di.provideSafeViewModel
import me.app.yummy.presentation.wizard.questionnaire.screen.WDietaryScreen
import me.app.yummy.presentation.wizard.questionnaire.screen.WExperienceScreen
import me.app.yummy.presentation.wizard.questionnaire.screen.WGoalScreen
import me.app.yummy.presentation.wizard.questionnaire.screen.WSkillScreen

sealed class TextType(open val text: String) {
    data class NormalText(override val text: String) : TextType(text)
    data class GradientText(override val text: String) : TextType(text)
}

// == HANDLE NAVIGATION WITH 'HorizontalPager' == //
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun QuestionnaireNavigation(
    finalize: () -> Unit,
    vm: QuestionnaireViewModel = provideSafeViewModel<QuestionnaireViewModel>()
) {
    val pagerState = rememberPagerState { 4 }
    val scope = rememberCoroutineScope()

    // == N.B do not remove strings spaces == //
    // == N.B do not remove strings spaces == //
    val texts = arrayOf(
        listOf(TextType.NormalText("Select your "), TextType.GradientText("favorite cuisines")),
        listOf(TextType.NormalText("Select your cooking "), TextType.GradientText("experience")),
        listOf(TextType.NormalText("Select any dietary "), TextType.GradientText("restrictions")),
        listOf(
            TextType.NormalText("What is your "),
            TextType.GradientText("main goal "),
            TextType.NormalText("in the app?")
        )
    )

    // Memorize the brush to avoid recreation every frame
    val gradientBrush = remember { textBrush }

    // Local scope //
    fun next() {
        scope.launch {
            if (pagerState.currentPage < 3) pagerState.animateScrollToPage(pagerState.currentPage + 1)
            else finalize()
        }
    }

    Box(Modifier.fillMaxSize()) {
        Spacer(Modifier.height(16.dp))

        // Main pager
        HorizontalPager(
            state = pagerState,
            userScrollEnabled = false,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            AnimatedContent(
                targetState = page,
                transitionSpec = {
                    slideInVertically { height -> height } + fadeIn() with
                            slideOutVertically { height -> -height } + fadeOut()
                }
            ) { targetPage ->
                Column(Modifier.fillMaxSize().padding(horizontal = LATERAL_PADDING)) {
                    val row = texts[targetPage]
                    Text(
                        modifier = Modifier.padding(top = 32.dp, bottom = 20.dp),
                        text = buildAnnotatedString {
                            row.forEach { t ->
                                when (t) {
                                    is TextType.NormalText -> pushStyle(
                                        SpanStyle(
                                            fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                                            fontWeight = FontWeight.Bold
                                        )
                                    )
                                    is TextType.GradientText -> pushStyle(
                                        SpanStyle(
                                            brush = gradientBrush,
                                            fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                                            fontWeight = FontWeight.Bold
                                        )
                                    )
                                }
                                append(t.text)
                                pop()
                            }
                        }
                    )

                    // == SHOW PROGRESS ==
                    LinearProgressIndicator(
                        progress = (targetPage + 1) / 4f,
                        modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                        color = MaterialTheme.colorScheme.secondary,
                        trackColor = MaterialTheme.colorScheme.surface
                    )

                    // == SHOW SCREEN PER PAGE ==
                    when (targetPage) {
                        0 -> WSkillScreen { vm.saveSkill(it); next() }
                        1 -> WExperienceScreen { vm.saveExperience(it); next() }
                        2 -> WDietaryScreen { vm.saveDietary(it); next() }
                        3 -> WGoalScreen { vm.saveGoal(it); next() }
                    }
                }
            }
        }

        // LEFT BORDER
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(LATERAL_PADDING)
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.background,
                            Color.Transparent
                        )
                    )
                )
        )

        // RIGHT BORDER
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(LATERAL_PADDING)
                .align(Alignment.CenterEnd)
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            Color.Transparent,
                            MaterialTheme.colorScheme.background
                        )
                    )
                )
        )
    }
}
