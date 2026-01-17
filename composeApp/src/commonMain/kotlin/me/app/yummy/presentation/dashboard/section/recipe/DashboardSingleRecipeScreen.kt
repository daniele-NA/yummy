package me.app.yummy.presentation.dashboard.section.recipe

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import me.app.yummy.Recipe
import me.app.yummy.RecipeStep
import me.app.yummy.compose.theme.LATERAL_PADDING
import me.app.yummy.compose.theme.textBrush
import me.app.yummy.compose.widgets.animations.TopAnimated
import me.app.yummy.core.result.ResultState
import org.jetbrains.compose.resources.painterResource
import yummy.composeapp.generated.resources.Res
import yummy.composeapp.generated.resources.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardSingleRecipeScreen(id: Long, vm: SingleRecipeViewModel) {
    var recipe by remember { mutableStateOf<Recipe?>(null) }
    var recipeSteps by remember { mutableStateOf<List<RecipeStep>>(emptyList()) }

    LaunchedEffect(id) {
        vm.retrieveRecipe(id) { r ->
            when (r) {
                is ResultState.Error -> recipe = null
                is ResultState.Success<*> -> {
                    recipe = r.data as Recipe

                    // == AFTER THE RECIPE IS PRESENT == //
                    vm.getSteps {
                        if (it is ResultState.Success) recipeSteps = it.data
                    }
                }

                else -> Unit
            }
        }
    }

    val titleFontSize = 24.sp

    var isAnimationStarted by rememberSaveable {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        isAnimationStarted = true
    }


    recipe?.let { r ->


        // IMAGE WITH BRUSH //
        Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
            Box(modifier = Modifier.fillMaxWidth().height(300.dp)) {
                KamelImage(
                    { asyncPainterResource(r.previewUrl) },
                    contentDescription = r.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    MaterialTheme.colorScheme.background.copy(alpha = 0.2f),
                                    MaterialTheme.colorScheme.background
                                ),
                                startY = 180f
                            )
                        )

                )
            }
            // == Main Content == //
            Column(modifier = Modifier.padding(16.dp)) {
                // == title == //
                TopAnimated(isAnimationStarted) {
                    Text(text = buildAnnotatedString {
                        withStyle(
                            style = ParagraphStyle(lineHeight = 32.sp)
                        ) {
                            pushStyle(
                                SpanStyle(
                                    brush = textBrush,
                                    fontSize = titleFontSize,
                                    fontWeight = FontWeight.Bold,
                                )
                            )
                            append(r.title)
                            pop()
                        }
                    })
                }


                // == pair of cards == //
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 26.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 8.dp),
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Column(
                            Modifier.fillMaxSize().padding(vertical = LATERAL_PADDING * 2),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                modifier = Modifier.size(110.dp).padding(bottom = 5.dp),
                                painter = painterResource(Res.drawable.timelapse_ic),
                                contentDescription = "${r.requiredTimeMin} min",
                            )
                            Text(
                                "${r.requiredTimeMin} min",
                                style = MaterialTheme.typography.bodyMedium,
                                overflow = TextOverflow.Ellipsis
                            )
                        }

                    }

                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 8.dp),
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Column(
                            Modifier.fillMaxSize().padding(vertical = LATERAL_PADDING * 2),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                modifier = Modifier.size(110.dp).padding(bottom = 5.dp),
                                painter = painterResource(Res.drawable.forc_ic),
                                contentDescription = r.category,
                            )
                            Text(
                                r.category,
                                style = MaterialTheme.typography.bodyMedium,
                                overflow = TextOverflow.Ellipsis
                            )
                        }

                    }
                }


                // == Steps == //

                Text(
                    modifier = Modifier.padding(bottom = 10.dp),
                    text = "Directions :",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Start
                )


                recipeSteps.forEach { step ->

                    Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = LATERAL_PADDING),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {


                        // == ORDER == //
                        Text(
                            modifier = Modifier.padding(horizontal = 12.dp),
                            text = step.order.toString(),
                            style = MaterialTheme.typography.bodyLarge
                        )

                        // == PADDING ON THIS VIEW,DOESN'T WORK == //
                        Box(
                            modifier = Modifier
                                .width(2.dp)
                                .height(40.dp)
                                .background(MaterialTheme.colorScheme.secondary)
                        )

                        Text(
                            modifier = Modifier.padding(horizontal = 12.dp),
                            text = step.description,
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontSize = 20.sp,
                                lineHeight = 30.sp
                            )
                        )
                    }
                }
            }
        }
    }
}

