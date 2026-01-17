package me.app.yummy.presentation.dashboard

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.app.yummy.Recipe
import me.app.yummy.core.LOG
import me.app.yummy.data.local.db.DbRepo
import me.app.yummy.data.local.preferences.PreferencesRepo


class DashboardViewModel(private val prefs: PreferencesRepo, private val dbRepo: DbRepo) :
    ViewModel() {

    // == OBSERVABLE ONLY BY COMPOSABLE FUNCTIONS == //
    var recipes by mutableStateOf<List<Recipe>>(emptyList())
        private set


    var searchQuery by mutableStateOf("")
        private set

    var filteredRecipes by mutableStateOf<List<Recipe>>(emptyList())
        private set


    // == Must implement also the research referencing the RecipeStep params == //
    fun onSearchQueryChange(query: String) {
        if (query.isEmpty()) {
            filteredRecipes = recipes
        }
        searchQuery = query
        filteredRecipes =
            if (query.isBlank()) recipes
            else recipes.filter {
                it.title.contains(query, ignoreCase = true) ||
                        it.category.contains(query, ignoreCase = true)
            }
    }


    fun initPlaceholder() {
        viewModelScope.launch {

            val c= dbRepo.countRecipes().toInt()
            LOG("DbItemsCount => $c}")

            if(c>=5) return@launch

            dbRepo.insertRecipe(
                title = "Pasta with Tomato Sauce",
                previewUrl = "https://www.themealdb.com/images/media/meals/ustsqw1468250014.jpg",
                category = "main-course",
                requiredTimeMin = 45,
                preferred = true
            )?.let { recipes += it }

            dbRepo.insertRecipe(
                title = "Tomato Soup",
                previewUrl = "https://www.themealdb.com/images/media/meals/3m8yae1763257951.jpg",
                category = "starter",
                requiredTimeMin = 30,
                preferred = false
            )?.let { recipes += it }

            dbRepo.insertRecipe(
                title = "Stuffed Rolls",
                previewUrl = "https://www.themealdb.com/images/media/meals/grhn401765687086.jpg",
                category = "main-course",
                requiredTimeMin = 20,
                preferred = false
            )?.let { recipes += it }

            dbRepo.insertRecipe(
                title = "Grilled Lamb Skewers",
                previewUrl = "https://www.themealdb.com/images/media/meals/04axct1763793018.jpg",
                category = "main-course",
                requiredTimeMin = 50,
                preferred = false
            )?.let { recipes += it }

            dbRepo.insertRecipe(
                title = "Baked Potatoes",
                previewUrl = "https://www.themealdb.com/images/media/meals/5jdtie1763289302.jpg",
                category = "side-dish",
                requiredTimeMin = 60,
                preferred = false
            )?.let { recipes += it }

        }
    }

    fun loadPlaceHolder() {
        viewModelScope.launch {
            dbRepo.getAllRecipes().collect { list ->
                recipes = list
                filteredRecipes = list
            }
        }
    }


    fun togglePreferredState(id:Long,actualPreferredState: Long){
        viewModelScope.launch {
            val newPreferred = if (actualPreferredState == 1L) 0L else 1L


            dbRepo.updateRecipePreferredStateById(id,newPreferred)

            recipes = recipes.map { recipe ->
                if (recipe.id == id) recipe.copy(preferred = newPreferred)
                else recipe
            }
        }
    }


}