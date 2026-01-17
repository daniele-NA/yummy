@file:Suppress("FunctionName")
package me.app.yummy.core

const val LOG_TAG = "MY-LOG"
// == Multi Platform log == //
expect fun LOG(value: String?)

enum class Platform{ ANDROID , IOS}

expect fun getPlatform(): Platform

expect fun nativeSystemTimeMillis () :Long