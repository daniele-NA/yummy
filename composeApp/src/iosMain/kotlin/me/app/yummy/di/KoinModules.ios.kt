package me.app.yummy.di

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.CreationExtras
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import me.app.yummy.YummyDb
import me.app.yummy.presentation.auth.AuthViewModel
import me.app.yummy.presentation.dashboard.DashboardViewModel
import me.app.yummy.presentation.dashboard.section.recipe.SingleRecipeViewModel
import me.app.yummy.presentation.wizard.questionnaire.QuestionnaireViewModel
import org.koin.compose.getKoin
import org.koin.dsl.module


// into ios we do not have real android viewModels, so we declare it as singleton //
actual val viewModelModule = module {
    single { DashboardViewModel(get(),get()) }
    single { AuthViewModel(get()) }
    single { QuestionnaireViewModel() }
    single { SingleRecipeViewModel(get()) }
}

@Composable
actual inline fun <reified T : ViewModel> providePlatformViewModel(
    viewModelStoreOwner: ViewModelStoreOwner,
    key: String?,
    factory: ViewModelProvider.Factory?,
    extras: CreationExtras?
): T {
    return getKoin().get<T>()
}

actual fun provideDb(): YummyDb = YummyDb(NativeSqliteDriver(YummyDb.Schema, DATABASE_NAME))
