package me.app.yummy.presentation.dashboard.section.root

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import me.app.yummy.compose.theme.LATERAL_PADDING
import me.app.yummy.core.notify.showToast
import me.app.yummy.presentation.dashboard.section.DashboardCardPreview
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import yummy.composeapp.generated.resources.Res
import yummy.composeapp.generated.resources.arrow_ic
import yummy.composeapp.generated.resources.fish_preview
import yummy.composeapp.generated.resources.meat_preview
import yummy.composeapp.generated.resources.salad_preview
import yummy.composeapp.generated.resources.spaghetti_preview
import yummy.composeapp.generated.resources.sweet_preview

@Composable
fun DashboardPredefinedRecipes() {
    Row(
        modifier = Modifier.fillMaxWidth().padding(bottom = 15.dp)
            .padding(horizontal = LATERAL_PADDING),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.padding(end = 20.dp),
            text = "Recommended for you",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground
        )

        IconButton(onClick = {
            /* TODO */
            showToast("Coming Soon")
        }) {
            Icon(
                painter = painterResource(Res.drawable.arrow_ic),
                contentDescription = null
            )
        }
    }

    data class DashboardCardData(
        val imageRes: DrawableResource,
        val title: String,
        val category: String,
        val requiredTime: String,
        val rating: Float
    )

    // fixme placeholder //
    val sampleCards = listOf(
        DashboardCardData(Res.drawable.meat_preview, "Grilled Steak", "Meat", "30 min", 4.6f),
        DashboardCardData(Res.drawable.sweet_preview, "Chocolate Cake", "Dessert", "25 min", 4.2f),
        DashboardCardData(Res.drawable.salad_preview, "Mixed Salad", "Vegetarian", "15 min", 4.0f),
        DashboardCardData(
            Res.drawable.spaghetti_preview,
            "Spaghetti Bolognese",
            "Pasta",
            "20 min",
            4.3f
        ),
        DashboardCardData(Res.drawable.fish_preview, "Grilled Salmon", "Fish", "35 min", 4.7f)
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(MaterialTheme.colorScheme.background)
    ) {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(sampleCards.size) { index ->

                // PADDING FOR FIRST & LAST ELEMENT //
                val itemModifier = Modifier
                    .padding(
                        start = if (index == 0) LATERAL_PADDING else 0.dp,
                        end = if (index == sampleCards.lastIndex) LATERAL_PADDING else 0.dp
                    )
                DashboardCardPreview(
                    modifier = itemModifier,
                    imageRes = sampleCards[index].imageRes,
                    title = sampleCards[index].title,
                    category = sampleCards[index].category,
                    requiredTime = sampleCards[index].requiredTime,
                    rating = sampleCards[index].rating,
                    onClick = {
                        showToast(message = "Coming Soon",)
                    }
                )
            }
        }

        // LEFT BORDER
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(32.dp)
                .background(
                    Brush.horizontalGradient(
                        listOf(
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
                .width(32.dp)
                .align(Alignment.CenterEnd)
                .background(
                    Brush.horizontalGradient(
                        listOf(
                            Color.Transparent,
                            MaterialTheme.colorScheme.background
                        )
                    )
                )
        )
    }



}