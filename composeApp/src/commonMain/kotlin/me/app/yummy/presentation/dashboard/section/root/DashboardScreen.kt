package me.app.yummy.presentation.dashboard.section.root

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.app.yummy.compose.theme.BUTTON_HEIGHT
import me.app.yummy.compose.theme.LATERAL_PADDING
import me.app.yummy.compose.widgets.animations.BottomAnimated
import me.app.yummy.compose.widgets.animations.TopAnimated
import me.app.yummy.presentation.dashboard.DashboardViewModel
import org.jetbrains.compose.resources.painterResource
import yummy.composeapp.generated.resources.Res
import yummy.composeapp.generated.resources.search_ic

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    vm: DashboardViewModel,
    onNavigateToSingleRecipe: (id: Long) -> Unit,
    onNavigateToRecipes: (openSearchBar: Boolean) -> Unit
) {


    var isAnimationStarted by rememberSaveable {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        isAnimationStarted = true
    }


    LazyColumn(
        modifier = Modifier.fillMaxHeight(),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {

        item {
            TopAnimated(isAnimationStarted) {
                DashboardHeader(Modifier.padding(horizontal = LATERAL_PADDING)) {
                    "Daniel"
                }
            }


        }

        // == WE NAVIGATE INTO RecipesScreen WITH openSearchBar flag set to true == //
        item {
            Button(
                onClick = {
                    onNavigateToRecipes(true)
                },
                modifier = Modifier
                    .padding(
                        bottom = 40.dp,
                        start = LATERAL_PADDING,
                        end = LATERAL_PADDING
                    )
                    .fillMaxWidth()
                    .height(BUTTON_HEIGHT)
            ) {
                Icon(painter = painterResource(Res.drawable.search_ic), contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Start Your Search", style = MaterialTheme.typography.bodyMedium)
            }
        }

        item {
            DashboardPredefinedRecipes()
        }
        item {
            Spacer(Modifier.padding(vertical = 9.dp))

        }
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 30.dp)
                    .padding(horizontal = LATERAL_PADDING),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier.alignByBaseline(),
                    text = "Your recipes",
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    modifier = Modifier
                        .alignByBaseline()
                        .clickable {
                            onNavigateToRecipes(false)
                        },
                    text = "See all",
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 20.sp),
                    color = Color(0xFF757575)
                )
            }
        }


        // == SHOW ALL USER'S RECIPES == //
        // == MUST USE  method : items( items: List<T>,noinline key: ((item: T) -> Any)? = null (AVOID rc) == //
        items(vm.recipes, key = { it.id }) { item ->
            DashboardTile(recipe = item, onNavigateToSingleRecipe = { id ->
                onNavigateToSingleRecipe(id)
            }, onTogglePreferredState = { id, actualState ->
                vm.togglePreferredState(id, actualState)
            })
        }
    }

}
