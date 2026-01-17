package me.app.yummy.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable
import me.app.yummy.compose.theme.AppTheme
import me.app.yummy.compose.widgets.animations.AnimatedNavHost
import me.app.yummy.di.provideSafeViewModel
import me.app.yummy.presentation.auth.AuthScreen
import me.app.yummy.presentation.auth.AuthViewModel
import me.app.yummy.presentation.dashboard.DashboardNavigation
import me.app.yummy.presentation.wizard.questionnaire.QuestionnaireNavigation

@Serializable
private object AuthPage
@Serializable
private object DashboardNav
@Serializable
private object QuestionnaireNav

// == UI App Entrypoint == //
@Composable
fun BaseScreen() {

    AppTheme {
        Scaffold { safePadding ->
            val navController = rememberNavController()

            // AuthPage -> QuestionnaireNav -> DashboardNav //
            AnimatedNavHost(
                modifier = Modifier.padding(safePadding).fillMaxSize(),
                navController = navController,
                startDestination = AuthPage
            ) {
                composable<QuestionnaireNav> {
                    QuestionnaireNavigation(finalize = {

                        // Pop last wizard page & AuthPage (Which is the root) //
                        navController.popBackStack()
                        navController.popBackStack()
                        navController.navigate(DashboardNav)
                    })
                }

                // == IF is the first boot , we'll be redirected to 'QuestionnaireNav' == //
                composable<AuthPage> {
                    val authViewModel: AuthViewModel = provideSafeViewModel(viewModelStoreOwner = it)
                    AuthScreen { email, password ->
                        authViewModel.tryAuth(email, password).onSuccess {

                            // fixme (decommenta)
//                            authViewModel.mustNavigateToWizard {
//                                navController.navigate(QuestionnaireNav)
//                            }

                            navController.navigate(QuestionnaireNav)

                        }
                    }
                }
                composable<DashboardNav> {
                    DashboardNavigation()
                }


            }
        }
    }

}

