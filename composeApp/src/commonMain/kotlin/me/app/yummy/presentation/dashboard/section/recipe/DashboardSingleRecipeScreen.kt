package me.app.yummy.presentation.dashboard.section.recipe

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import me.app.yummy.Recipe

@Composable
fun DashboardSingleRecipeScreen(id: Long, vm: SingleRecipeViewModel) {
    val recipe = remember { mutableStateOf<Recipe?>(null) }

    LaunchedEffect(id) { vm.retrieveRecipe(id) { recipe.value = it } }

    recipe.value?.let { r ->
        Column(modifier = Modifier.fillMaxSize()) {
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
                                colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.5f)),
                                startY = 150f
                            )
                        )
                )
            }
            Column(modifier = Modifier.padding(16.dp)) {
                Text(r.title, style = MaterialTheme.typography.titleLarge, color = Color.White)




               // Text("Required Time: ${r.requiredTimeMin} min", color = Color.White)
            }
        }
    }
}

