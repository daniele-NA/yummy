package me.app.yummy.presentation.dashboard.section.root

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import me.app.yummy.Recipe
import me.app.yummy.compose.theme.LATERAL_PADDING
import me.app.yummy.compose.widgets.CardBlur
import org.jetbrains.compose.resources.painterResource
import yummy.composeapp.generated.resources.Res
import yummy.composeapp.generated.resources.arrow_ic
import yummy.composeapp.generated.resources.favorite_empty_ic
import yummy.composeapp.generated.resources.favorite_filled_ic
import yummy.composeapp.generated.resources.timelapse_ic

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardTile(
    modifier: Modifier = Modifier,
    recipe: Recipe,
    onNavigateToSingleRecipe: (id: Long) -> Unit,
    onTogglePreferredState:(id: Long,actualState:Long)->Unit
) {
    // == Do not invert the clip() method (fundamental for ripple) == //
    val interactionSource = remember { MutableInteractionSource() }
    var animateBounce by rememberSaveable { mutableStateOf(false) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = LATERAL_PADDING).padding(bottom = 8.dp)
            .clip(RoundedCornerShape(20.dp))
            .clickable(
                interactionSource = interactionSource,
                onClick = { onNavigateToSingleRecipe(recipe.id) }
            ),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {


        // == wrap the entire card content == //
        Box(modifier = Modifier.fillMaxWidth()) {

            // Main content ///
            Row(
                modifier = Modifier
                    .fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                // LEFT: image + preferred
                Box(
                    modifier = Modifier
                        .size(width = 120.dp, height = 180.dp)
                ) {
                    KamelImage(
                        { asyncPainterResource(recipe.previewUrl) },
                        contentDescription = recipe.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )

                    CardBlur(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(6.dp)
                    ) {

                        // == CHOOSE THE ICON BASED ON 'preferred' VALUE + BOUNCE == //
                        IconButton(
                            modifier = Modifier.size(36.dp),
                            onClick = {
                                onTogglePreferredState(recipe.id, recipe.preferred)

                                // == ONLY IF WE'RE SETTING 'preferredState' TO 1L == //
                                if (recipe.preferred == 0L) animateBounce = true
                            }) {
                            val targetScale = if (animateBounce) 1.4f else 1f
                            val scale by animateFloatAsState(
                                targetValue = targetScale,
                                animationSpec = tween(durationMillis = 150),
                                finishedListener = { animateBounce = false }
                            )

                            Icon(
                                painter = painterResource(
                                    if (recipe.preferred.toInt() == 0)
                                        Res.drawable.favorite_empty_ic
                                    else
                                        Res.drawable.favorite_filled_ic
                                ),
                                contentDescription = null,
                                tint = null,
                                modifier = Modifier
                                    .size(11.dp)
                                    .padding(top = 3.dp)
                                    .graphicsLayer {
                                        scaleX = scale
                                        scaleY = scale
                                    }
                            )
                        }
                    }
                }

                // RIGHT: text column
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)
                        .padding(LATERAL_PADDING),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = recipe.title,
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                    )

                    Spacer(Modifier.height(6.dp))

                    // == Obviously this name must be the 'author' name == //
                    Text(
                        text = "By Daniele",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )

                    Column(
                        modifier = Modifier.fillMaxWidth().padding(top = 15.dp),
                    ) {


                        // requiredTime Tile //
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                modifier = Modifier.padding(end = 4.dp),
                                painter = painterResource(Res.drawable.timelapse_ic),
                                contentDescription = null,
                                tint = null
                            )
                            Text(
                                text = "${recipe.requiredTimeMin} min",
                                style = MaterialTheme.typography.bodySmall.copy(fontSize = 18.sp),
                            )

                        }

                        Spacer(Modifier.weight(1f))


                        // category text //
                        Text(
                            modifier = Modifier.padding(vertical = 4.dp),
                            text = recipe.category,
                            style = MaterialTheme.typography.bodySmall.copy(fontSize = 16.sp),
                            color = Color.Gray
                        )


                    }
                }
            }


            // == trailing padding == //
//            Icon(
//                painter = painterResource(Res.drawable.arrow_ic),
//                contentDescription = null,
//                tint = MaterialTheme.colorScheme.onSurface,
//                modifier = Modifier
//                    .align(Alignment.BottomEnd)
//                    .padding(12.dp)
//            )
        }

    }

}
