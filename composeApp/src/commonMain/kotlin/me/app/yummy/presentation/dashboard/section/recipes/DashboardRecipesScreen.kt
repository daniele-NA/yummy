package me.app.yummy.presentation.dashboard.section.recipes

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import me.app.yummy.compose.theme.LATERAL_PADDING
import me.app.yummy.presentation.dashboard.DashboardViewModel
import org.jetbrains.compose.resources.painterResource
import yummy.composeapp.generated.resources.Res
import yummy.composeapp.generated.resources.timelapse_ic
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import yummy.composeapp.generated.resources.main_ic


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardRecipesScreen(
    openSearchBar: Boolean,
    vm: DashboardViewModel,
    onNavigateToSingleRecipe: (id: Long) -> Unit
) {
    var isSearchVisible by rememberSaveable { mutableStateOf(openSearchBar) }

    val focusRequester = remember { FocusRequester() }
    val keyboard = LocalSoftwareKeyboardController.current

    LaunchedEffect(isSearchVisible) {
        if (isSearchVisible) {
            focusRequester.requestFocus()
            keyboard?.show()
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(LATERAL_PADDING),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(Res.drawable.main_ic),
                contentDescription = "Yummy",
                modifier = Modifier.size(48.dp),
                contentScale = ContentScale.Fit
            )
            OutlinedTextField(
                value = vm.searchQuery,
                onValueChange = vm::onSearchQueryChange,
                textStyle = MaterialTheme.typography.bodyMedium.copy(
                    lineHeight = 0.sp
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .focusRequester(focusRequester),
                placeholder = { Text("Search recipes") },
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.background,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                    cursorColor = MaterialTheme.colorScheme.secondary
                )
            )
        }



        LazyColumn {
            items(vm.filteredRecipes, key = { it.id }) { recipe ->
                // == Do not invert the clip() method (fundamental for ripple) == //
                val interactionSource = remember { MutableInteractionSource() }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = LATERAL_PADDING).padding(bottom = 8.dp)
                        .animateItem()
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
                                    .size(width = 120.dp, height = 130.dp)
                            ) {
                                KamelImage(
                                    { asyncPainterResource(recipe.previewUrl) },
                                    contentDescription = recipe.title,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
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
                                }
                            }
                        }
                    }

                }

            }
        }
    }
}
