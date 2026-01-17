package me.app.yummy.compose.widgets

import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.UIKitView
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.readValue
import platform.WebKit.WKWebView
import platform.WebKit.WKWebViewConfiguration
import platform.WebKit.WKPreferences
import platform.WebKit.javaScriptEnabled

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun NativeWebView(url: String) {
    UIKitView(
        factory = {
            val config = WKWebViewConfiguration().apply {
                preferences = WKPreferences().apply {
                    javaScriptEnabled = true
                }
            }
            WKWebView(frame = platform.CoreGraphics.CGRectZero.readValue(), configuration = config).apply {
                loadRequest(
                    platform.Foundation.NSURLRequest(
                        platform.Foundation.NSURL.URLWithString(url)!!
                    )
                )
            }
        },
        update = { webView ->
            webView.loadRequest(
                platform.Foundation.NSURLRequest(
                    platform.Foundation.NSURL.URLWithString(url)!!
                )
            )
        }
    )
}
