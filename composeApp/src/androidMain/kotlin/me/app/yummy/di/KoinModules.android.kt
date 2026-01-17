package me.app.yummy.di

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.CreationExtras
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import me.app.yummy.YummyDb
import me.app.yummy.boot.YummyApp.Companion.appContext
import me.app.yummy.presentation.auth.AuthViewModel
import me.app.yummy.presentation.dashboard.DashboardViewModel
import me.app.yummy.presentation.dashboard.section.recipe.SingleRecipeViewModel
import me.app.yummy.presentation.wizard.questionnaire.QuestionnaireViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

actual val viewModelModule = module {
    viewModel { DashboardViewModel(get(),get()) }
    viewModel { AuthViewModel(get()) }
    viewModel { QuestionnaireViewModel() }
    viewModel { SingleRecipeViewModel(get()) }
}

@Composable
actual inline fun <reified T : ViewModel> providePlatformViewModel(
    viewModelStoreOwner: ViewModelStoreOwner,
    key: String?,
    factory: ViewModelProvider.Factory?,
    extras: CreationExtras?
): T {
    return koinViewModel<T>()
}

actual fun provideDb() = YummyDb(AndroidSqliteDriver(YummyDb.Schema, appContext, DATABASE_NAME))
