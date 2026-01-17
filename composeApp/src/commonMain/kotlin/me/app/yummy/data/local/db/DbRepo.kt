package me.app.yummy.data.local.db

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import me.app.yummy.YummyDb
import me.app.yummy.Recipe
import me.app.yummy.RecipeStep
import me.app.yummy.core.nativeSystemTimeMillis

// == WE DO NOT NEED ALL THE PARAMS OF 'RecipeStep' == //
data class StepEntry(
    public val description: String,
    public val durationMin: Long?,
)

class DbRepo(private val db: YummyDb) {

    private val recipeQueries = db.recipeQueries
    private val recipeStepQueries = db.recipeStepQueries

    // Missing url check consistency etc etc //
    suspend fun insertRecipe(
        title: String,
        previewUrl: String,
        category: String,
        requiredTimeMin: Long,
        preferred: Boolean,
        stepEntries: List<StepEntry>
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

            val recipeInserted = recipeQueries.getLastInsertedRecipe().executeAsOneOrNull()

            // == AFTER WE SURE THAT THE RECIPE IS VALID,WE ADD STEPS == //
            recipeInserted?.let { safeRecipeInserted->
                stepEntries.forEachIndexed { index,step ->
                    recipeStepQueries.insertRecipeStep(
                        id = null,
                        recipeId = safeRecipeInserted.id,

                        // WE REFERENCE THE recipeInserted //
                        order = index.toLong(),
                        description = step.description,
                        durationMin = step.durationMin
                    )
                }
            }


            recipeInserted
        }
    }

    suspend fun getStepsByRecipeId(id: Long): List<RecipeStep> = withContext(Dispatchers.IO) {
        return@withContext recipeStepQueries.getStepsByRecipe(id).executeAsList()
    }

    suspend fun getRecipeById(id: Long): Recipe? = withContext(Dispatchers.IO) {
        recipeQueries.getRecipeById(id).executeAsOneOrNull()
    }

    suspend fun updateRecipePreferredStateById(id: Long, newPreferred: Long) =
        withContext(Dispatchers.IO) {
            recipeQueries.updateRecipePreferredById(id = id, preferred = newPreferred)
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
