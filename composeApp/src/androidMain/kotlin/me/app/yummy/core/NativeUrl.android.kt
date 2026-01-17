package me.app.yummy.core

import android.content.Intent
import androidx.core.net.toUri
import me.app.yummy.MainActivity

actual fun navigateWithUrl(url: String) {
    MainActivity.mainContext?.startActivity(Intent(Intent.ACTION_VIEW, url.toUri()))
}

