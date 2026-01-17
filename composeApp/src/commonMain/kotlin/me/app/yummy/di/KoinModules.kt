package me.app.yummy.di


import androidx.compose.runtime.Composable
import androidx.lifecycle.HasDefaultViewModelProviderFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import com.russhwolf.settings.Settings
import me.app.yummy.YummyDb
import me.app.yummy.core.LOG
import me.app.yummy.data.local.db.DbRepo
import me.app.yummy.data.local.preferences.PreferencesRepo
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module



// ==                       ViewModels                                  == //
expect val viewModelModule: Module

@Composable
expect inline fun <reified T : ViewModel> providePlatformViewModel(
    viewModelStoreOwner: ViewModelStoreOwner,
    key: String?,
    factory: ViewModelProvider.Factory?,
    extras: CreationExtras?
): T

// == DI-COMPOSE WRAPPER (AVOID IOS MISSING SYMBOL)  == //
@Composable
internal inline fun <reified T : ViewModel> provideSafeViewModel(
    viewModelStoreOwner: ViewModelStoreOwner =
        checkNotNull(LocalViewModelStoreOwner.current) {
            "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
        },
    key: String? = null,
    factory: ViewModelProvider.Factory? = null,
    extras: CreationExtras? = null
): T = providePlatformViewModel<T>(
    viewModelStoreOwner = viewModelStoreOwner,
    key = key,
    factory = factory,
    extras = extras ?: if (viewModelStoreOwner is HasDefaultViewModelProviderFactory) {
        viewModelStoreOwner.defaultViewModelCreationExtras
    } else {
        CreationExtras.Empty
    }
)




// ==                       DATABASE ('YummyDb' is auto-generated)          == //

const val DATABASE_NAME = "app_db"
expect fun provideDb(): YummyDb


// ==                       Singleton Repositories                      == //
private val repositories = module {
    single { Settings() }
    single { PreferencesRepo(settings = get()) }

    single { provideDb() }
    single { DbRepo(get<YummyDb>()) }
}

// ==                       INIT                                  == //
fun initKoin(config: KoinAppDeclaration? = null) {
    LOG("Initializing Koin")
    startKoin {
        config?.invoke(this)
        modules(repositories, viewModelModule)
    }
}
