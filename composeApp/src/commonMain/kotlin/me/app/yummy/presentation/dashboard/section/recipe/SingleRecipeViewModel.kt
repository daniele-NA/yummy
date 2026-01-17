package me.app.yummy.presentation.dashboard.section.recipe


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.app.yummy.Recipe
import me.app.yummy.RecipeStep
import me.app.yummy.core.result.ResultState
import me.app.yummy.data.local.db.DbRepo


// == HANDLE UI OPERATIONS FOR 'DashboardSingleRecipeScreen' == //
class SingleRecipeViewModel(private val dbRepo: DbRepo) : ViewModel() {

    private lateinit var actualRecipe: Recipe

    fun getSteps(result: (ResultState<List<RecipeStep>>) -> Unit) {
        viewModelScope.launch {
            if (!::actualRecipe.isInitialized) result(ResultState.Error(Exception("Steps non found")))
            else result(ResultState.Success(dbRepo.getStepsByRecipeId(actualRecipe.id)))
        }
    }

    fun retrieveRecipe(id: Long, recipe: (ResultState<Recipe>) -> Unit) {
        viewModelScope.launch {
            dbRepo.getRecipeById(id)?.let {
                actualRecipe = it
                recipe(ResultState.Success(it))
            } ?: recipe(ResultState.Error(Exception("Recipe not found")))
        }
    }

}