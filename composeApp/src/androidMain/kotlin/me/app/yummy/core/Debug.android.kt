package me.app.yummy.core

import android.util.Log

@Suppress("FunctionName")
actual fun LOG(value: String?) {
    Log.e(LOG_TAG,value.toString())
}

actual fun getPlatform() = Platform.ANDROID
actual fun nativeSystemTimeMillis(): Long {
    return System.currentTimeMillis()
}