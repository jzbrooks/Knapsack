package com.jzbrooks.readlater

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.jsoup.Jsoup

@Composable
fun ReadingScreen(content: String) {
    Text(
        Jsoup.parse(content).text(),
        modifier = Modifier.verticalScroll(rememberScrollState()),
    )
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    ReadingScreen("https://logos.com")
}
