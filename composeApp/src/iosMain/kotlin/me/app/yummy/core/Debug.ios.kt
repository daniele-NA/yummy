package me.app.yummy.core

import platform.Foundation.NSDate
import platform.Foundation.timeIntervalSince1970


@Suppress("FunctionName")

actual fun LOG(value: String?) {
     println(value ?: "null")  // Android Studio => stdout
}

actual fun getPlatform() = Platform.IOS
actual fun nativeSystemTimeMillis(): Long {
    return (NSDate().timeIntervalSince1970 * 1000).toLong()
}