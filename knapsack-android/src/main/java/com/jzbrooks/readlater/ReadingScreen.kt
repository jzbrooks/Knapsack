package com.jzbrooks.readlater

import android.util.Base64
import android.util.Base64.NO_PADDING
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun ReadingScreen(url: String) {
    AndroidView(factory = { context ->
        WebView(context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
            )

            webViewClient = WebViewClient()

            val encodedData = Base64.encodeToString(url.toByteArray(), NO_PADDING)
            loadData(encodedData, "text/html", "base64")
        }
    })
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    ReadingScreen("https://logos.com")
}