package me.app.yummy.presentation.dashboard

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable
import me.app.yummy.compose.widgets.animations.AnimatedNavHost
import me.app.yummy.di.provideSafeViewModel
import me.app.yummy.presentation.dashboard.section.recipe.DashboardSingleRecipeScreen
import me.app.yummy.presentation.dashboard.section.recipe.SingleRecipeViewModel
import me.app.yummy.presentation.dashboard.section.recipes.DashboardRecipesScreen
import me.app.yummy.presentation.dashboard.section.root.DashboardScreen

@Serializable private object DashboardRoot
@Serializable private data class SingleRecipePage(val id: Long)
@Serializable private data class RecipesPage(val openSearchBar: Boolean)



// == DASHBOARD NAVIGATION ( NO NAV HOST ) == //
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun DashboardNavigation() {


    val vm = provideSafeViewModel<DashboardViewModel>()


    /* == FIXME REMOVE THIS SHIT == */
    LaunchedEffect(Unit){
        vm.initPlaceholder()
        vm.loadPlaceHolder()
    }



    val navController = rememberNavController()

    AnimatedNavHost(
        modifier = Modifier.fillMaxSize(),
        navController = navController,
        startDestination = DashboardRoot
    ) {
        composable<DashboardRoot> {
            DashboardScreen(
                vm,
                // WE automatically open the search bar ID NEEDED BY UI callbacks//
                onNavigateToRecipes = {
                    openSearchBar ->
                    navController.navigate(RecipesPage(openSearchBar = openSearchBar))
                },
                // We navigate to a specific recipe //
                onNavigateToSingleRecipe = { id ->
                    navController.navigate(SingleRecipePage(id))
                })
        }

        // ==              WE TAKE FLAGS FROM NAVIGATION CLASSES            == //


        composable<RecipesPage> {
            val page = it.toRoute<RecipesPage>()
            DashboardRecipesScreen(page.openSearchBar,vm){
                id->navController.navigate(SingleRecipePage(id))
            }
        }

        composable<SingleRecipePage> {
            val page = it.toRoute<SingleRecipePage>()
            val vm = provideSafeViewModel<SingleRecipeViewModel>()
            DashboardSingleRecipeScreen(id = page.id,vm)
        }


    }


}
