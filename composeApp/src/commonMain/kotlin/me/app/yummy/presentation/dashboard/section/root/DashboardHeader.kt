package me.app.yummy.presentation.dashboard.section.root

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.app.yummy.compose.theme.textBrush
import me.app.yummy.compose.widgets.ProfileAvatar
import org.jetbrains.compose.resources.painterResource
import yummy.composeapp.generated.resources.Res
import yummy.composeapp.generated.resources.avatar_ic

@Composable
fun DashboardHeader(modifier: Modifier = Modifier, getUsername: () -> String) {
    val titleFontSize = 20.sp

    Row(
        modifier = modifier.fillMaxWidth().padding(top = 40.dp, bottom = 30.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text =
                buildAnnotatedString {
                    withStyle(
                        style = ParagraphStyle(lineHeight = 32.sp)
                    ) {
                        pushStyle(
                            SpanStyle(
                                fontSize = titleFontSize,
                                fontWeight = FontWeight.Bold,
                            )
                        )
                        append("Discover your\nnext recipe, ")
                        pop()

                        pushStyle(
                            SpanStyle(
                                brush = textBrush,
                                fontSize = titleFontSize,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        append(getUsername())
                        pop()
                    }
                }

        )


        ProfileAvatar(
            painter = painterResource(Res.drawable.avatar_ic),
            modifier = Modifier.size(40.dp),
            borderColor = Color.Magenta,
            borderWidth = 2.dp,
            startAngle = 60f,
            sweepAngle = 180f,
            onClick = { /* TODO */ }
        )

    }

}