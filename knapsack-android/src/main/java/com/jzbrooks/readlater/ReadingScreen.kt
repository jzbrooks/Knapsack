package com.jzbrooks.readlater

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.jsoup.Jsoup
import org.jsoup.select.NodeTraversor

@Composable
fun ReadingScreen(content: String) {
    val test = FormattingVisitor()
    NodeTraversor.traverse(test, Jsoup.parse(content).root())
    Text(
        test.toString(),
        modifier = Modifier.verticalScroll(rememberScrollState()),
    )
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    ReadingScreen("https://logos.com")
}
