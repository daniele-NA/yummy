package me.app.yummy.presentation.dashboard.section.recipe


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.app.yummy.Recipe
import me.app.yummy.data.local.db.DbRepo


// == HANDLE UI OPERATIONS FOR 'DashboardSingleRecipeScreen' == //
class SingleRecipeViewModel(private val dbRepo: DbRepo) : ViewModel() {


    fun retrieveRecipe(id: Long, recipe: (Recipe?) -> Unit) {
        viewModelScope.launch {
            recipe(dbRepo.getRecipeById(id))
        }
    }

}