package me.app.yummy.presentation.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import me.app.yummy.compose.theme.LATERAL_PADDING
import me.app.yummy.compose.theme.textBrush
import me.app.yummy.compose.widgets.animations.BottomAnimated
import me.app.yummy.compose.widgets.buttons.GradientButton
import me.app.yummy.compose.widgets.animations.LottieAnimation
import me.app.yummy.compose.widgets.animations.TopAnimated
import me.app.yummy.core.LOG

@Composable
internal fun AuthScreen(
    onTryingAuth: (email: String, password: String) -> Unit
) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }


    Column(
        modifier = Modifier
            .padding(horizontal = LATERAL_PADDING)
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        var isAnimationStarted by rememberSaveable {
            mutableStateOf(false)
        }

        LaunchedEffect(Unit) {
            isAnimationStarted = true
        }



        TopAnimated(isAnimationStarted) {
            Text(
                modifier = Modifier.padding(top = 115.dp, bottom = 30.dp),
                text =
                    buildAnnotatedString {
                        pushStyle(
                            SpanStyle(
                                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        append("Yum, ")
                        pop()

                        pushStyle(
                            SpanStyle(
                                brush = textBrush,
                                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        append("Yummy!")
                        pop()
                    }

            )
        }

        BottomAnimated(isAnimationStarted){
            Text(
                modifier = Modifier.padding(bottom = 50.dp).padding(horizontal = LATERAL_PADDING * 3),
                text = "Discover delicious recipes that make cooking easy",
                style = MaterialTheme.typography.bodyMedium,
                minLines = 2,
                textAlign = TextAlign.Center
            )
        }

        LottieAnimation(
            modifier = Modifier.size(300.dp).padding(bottom = 20.dp),
            path = "files/lottie_chef.json"
        )


        Spacer(Modifier.weight(1f))
        BottomAnimated(isAnimationStarted) {
            GradientButton(
                modifier = Modifier.padding(bottom = 100.dp),
                text = "Join beta"
            ) {
                LOG("Trying auth")
                onTryingAuth(email, password)
            }
        }
    }
}
