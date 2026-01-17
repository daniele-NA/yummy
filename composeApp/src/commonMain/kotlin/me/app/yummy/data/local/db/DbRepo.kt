package me.app.yummy.data.local.db

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import me.app.yummy.YummyDb
import me.app.yummy.Recipe
import me.app.yummy.core.nativeSystemTimeMillis

class DbRepo(private val db: YummyDb) {

    private val recipeQueries = db.recipeQueries

    // Missing url check consistency etc etc //
    suspend fun insertRecipe(
        title: String,
        previewUrl: String,
        category: String,
        requiredTimeMin: Long,
        preferred: Boolean
    ): Recipe? = withContext(Dispatchers.IO) {
        db.transactionWithResult {
            recipeQueries.insertRecipe(
                id = null, // auto-generated
                title = title,
                previewUrl = previewUrl,
                category = category,
                createdAt = nativeSystemTimeMillis(),
                requiredTimeMin = requiredTimeMin,
                preferred = if (preferred) 1 else 0
            )

            recipeQueries.getLastInsertedRecipe().executeAsOneOrNull()
        }
    }

    suspend fun getRecipeById(id: Long): Recipe? = withContext(Dispatchers.IO) {
        recipeQueries.getRecipeById(id).executeAsOneOrNull()
    }

    suspend fun updateRecipePreferredStateById(id: Long, newPreferred: Long)=
        withContext(Dispatchers.IO) {
            recipeQueries.updateRecipePreferredById(id= id, preferred = newPreferred)
        }

    // fixme
    suspend fun countRecipes(): Long = withContext(Dispatchers.IO) {
        recipeQueries.countRecipes().executeAsOne()
    }

    fun getAllRecipes(): Flow<List<Recipe>> =
        recipeQueries.getAllRecipes().asFlow().mapToList(Dispatchers.IO)

    suspend fun deleteRecipe(id: Long) = withContext(Dispatchers.IO) {
        recipeQueries.deleteRecipe(id)
    }
}
