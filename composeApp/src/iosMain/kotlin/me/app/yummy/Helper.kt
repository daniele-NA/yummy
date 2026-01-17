@file:Suppress("unused")
package me.app.yummy

import me.app.yummy.presentation.BaseScreen

import androidx.compose.ui.window.ComposeUIViewController
import me.app.yummy.di.initKoin

// == Ios ui EntryPoint == //
fun mainViewController() = ComposeUIViewController(configure = {
    initKoin()
}) { BaseScreen() }