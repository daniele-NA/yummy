package me.app.yummy.core

import platform.Foundation.NSURL
import platform.UIKit.UIApplication
import platform.Foundation.NSDictionary
import platform.Foundation.dictionary

actual fun navigateWithUrl(url: String) {
    val nsUrl = NSURL.URLWithString(url) ?: return

    val options = NSDictionary.dictionary()
    UIApplication.sharedApplication.openURL(nsUrl, options = options, completionHandler = { success ->
        LOG("Open URL with success: $success")
    })
}
