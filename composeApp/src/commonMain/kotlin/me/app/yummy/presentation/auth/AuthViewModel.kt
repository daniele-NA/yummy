package me.app.yummy.presentation.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.app.yummy.core.LOG
import me.app.yummy.core.result.EmptyResultState
import me.app.yummy.data.local.preferences.PreferencesRepo

class AuthViewModel(private val preferencesRepo: PreferencesRepo) : ViewModel() {

    init {
        LOG("Initializing AuthViewModel")
    }

    // OBSERVABLE ONLY BY COMPOSE //
    var isLogged by mutableStateOf(false)
        private set

    // == IF the 'firstBoot' is true , after the login,we navigate to wizard == //
    fun mustNavigateToWizard(asyncCallback: () -> Unit) {
        viewModelScope.launch {
            val isFirstBoot = preferencesRepo.getPrefValue<Boolean>(PreferencesRepo.FIRST_BOOT_KEY)

            if (isFirstBoot != null && isFirstBoot) {
                asyncCallback(); preferencesRepo.putPrefValue(PreferencesRepo.FIRST_BOOT_KEY, false);
                return@launch;
            }
        }
    }

    /* TODO */
    fun tryAuth(email: String, pwd: String): EmptyResultState {
        isLogged = true
        return EmptyResultState.Success
    }

}