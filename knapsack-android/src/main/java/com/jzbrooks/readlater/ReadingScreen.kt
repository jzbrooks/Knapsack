package com.jzbrooks.readlater

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jzbrooks.readlater.data.CachingEntryRepository
import com.jzbrooks.readlater.data.EntryRepository
import org.jsoup.Jsoup
import org.jsoup.select.NodeTraversor

@Composable
fun ReadingScreen(content: String) {
    Text(
        content,
        modifier = Modifier
            .padding(start = 16.dp, top = 0.dp, end = 16.dp, bottom = 16.dp)
            .verticalScroll(rememberScrollState()),
    )
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    ReadingScreen("<p>This is <b>big</b></p>")
}
