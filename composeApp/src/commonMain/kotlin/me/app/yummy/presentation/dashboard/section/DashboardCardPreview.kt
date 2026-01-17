package me.app.yummy.presentation.dashboard.section

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.app.yummy.compose.theme.textBrush
import me.app.yummy.compose.widgets.CardBlur
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import yummy.composeapp.generated.resources.Res
import yummy.composeapp.generated.resources.star_ic

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardCardPreview(
    modifier: Modifier = Modifier,
    imageRes: DrawableResource,
    title: String,
    category: String,
    requiredTime: String,
    rating: Float,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }

    Card(
        modifier = modifier
            .height(200.dp)
            .width(150.dp)
            .clip(RoundedCornerShape(20.dp))
            .clickable(interactionSource=interactionSource,onClick={
                onClick()
            }),
        shape = RoundedCornerShape(20.dp)
    ) {
        Box(Modifier.fillMaxSize()) {

            Image(
                painter = painterResource(imageRes),
                contentDescription = title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(6.dp)
            ) {
                CardBlur(Modifier.fillMaxWidth()) {

                    // Distanced from the top of Blur-Card //
                    Text(
                        modifier = Modifier.padding(top = 10.dp),
                        text = title,
                        minLines = 2,
                        maxLines = 2,
                        textAlign = TextAlign.Center,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = Color.Black
                    )

                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        text =
                            buildAnnotatedString {
                                pushStyle(
                                    SpanStyle(
                                        color = Color.Black,
                                        fontSize = MaterialTheme.typography.bodySmall.fontSize,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                                append(requiredTime)
                                pop()

                                pushStyle(
                                    SpanStyle(
                                        brush = textBrush,
                                        fontSize = MaterialTheme.typography.bodySmall.fontSize,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                                append(" | ")
                                pop()

                                pushStyle(
                                    SpanStyle(
                                        color = Color.Black,
                                        fontSize = MaterialTheme.typography.bodySmall.fontSize,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                                append(category)
                            }

                    )

                }
                Spacer(Modifier.weight(1f))
                CardBlur(
                    modifier = Modifier
                        .align(Alignment.End)
                        // == extern padding == //
                        .padding(6.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(
                            horizontal = 8.dp,
                            vertical = 4.dp
                        ), // internal padding //
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp) // trailing space
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.star_ic),
                            contentDescription = rating.toString(),
                            tint = Color(0xFFFFD700),
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = rating.toString(),
                            style = MaterialTheme.typography.bodySmall.copy(fontSize = 14.sp),
                            color = Color.Black
                        )
                    }
                }


            }
        }
    }
}
