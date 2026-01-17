package me.app.yummy.core.values

import android.os.Build

/**
 * Check versioni per Runtime Permissions
 */
internal object AndroidVersionManager {


    // == minore o uguale ad Android 10 == //
    internal inline fun <T> isAndroid10OrBelow(onSuccess: () -> T): T? {
        return if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q) { // Q = 29 (Android 10)
            onSuccess()
        } else null
    }

    // == maggiore o uguale ad Android 11 == //
    internal inline fun <T> isAndroid11OrAbove(onSuccess: () -> T): T? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) { // R = 30 (Android 11)
            onSuccess()
        } else null
    }

    internal inline fun <T> isAndroid12OrBelow(onSuccess: () -> T): T? {
        return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
            onSuccess()
        } else null
    }

    internal inline fun <T> isAndroid12OrAbove(onSuccess: () -> T): T? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            onSuccess()
        } else null
    }


    internal inline fun <T> isAndroid13OrAbove(onSuccess: () -> T): T? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            onSuccess()
        } else null
    }
    internal inline fun <T> isAndroid14OrAbove(onSuccess: () -> T): T? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            onSuccess()
        } else null
    }
    internal inline fun <T> isAndroid15OrAbove(onSuccess: () -> T): T? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.VANILLA_ICE_CREAM) {
            onSuccess()
        } else null
    }

}