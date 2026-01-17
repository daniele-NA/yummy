package me.app.yummy.compose.widgets

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView

@SuppressLint("SetJavaScriptEnabled")
@Composable
actual fun NativeWebView(url: String) {

    AndroidView(factory = {
        WebView(it).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            webViewClient= WebViewClient()
            settings.javaScriptEnabled=true
            settings.domStorageEnabled=true // LocalStorage or SessionStorage enabled
        }
    }, update = {
        it.loadUrl(url)
    })
}