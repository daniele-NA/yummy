@file:Suppress("InlinedApi")

package me.app.yummy

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.ui.graphics.Color
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import me.app.yummy.core.LOG
import me.app.yummy.core.notify.NotificationDuration
import me.app.yummy.core.notify.showToast
import me.app.yummy.presentation.BaseScreen

// == MainActivity With Native Splash == //
class MainActivity : ComponentActivity() {

    // == FIXME : use appContext to send notifications == //
    companion object {
        @SuppressLint("StaticFieldLeak")
        internal var mainContext: Activity? = null
    }

    private val notificationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (granted) {
                LOG("Permission Granted")
            } else {
                showToast("Permessi negati", NotificationDuration.SHORT)
                LOG("Permission Denied")
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        mainContext = this
        setContent { BaseScreen() }

        // native permissions handling == //
        notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
    }

}
