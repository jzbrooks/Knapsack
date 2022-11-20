package com.jzbrooks.readlater

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import com.jzbrooks.readlater.data.EntryRepository
import org.jsoup.Jsoup
import org.jsoup.select.NodeTraversor

@Composable
fun ReadingScreen(backStackEntry: NavBackStackEntry, repository: EntryRepository) {
    val id = backStackEntry.arguments?.getLong("id")!!
    val formattedContent = rememberSaveable { mutableStateOf("") }

    LaunchedEffect(id) {
        val entry = repository.getEntry(id)

        if (entry?.content != null) {
            val basicFormatter = FormattingVisitor()
            NodeTraversor.traverse(basicFormatter, Jsoup.parse(entry.content).root())
            formattedContent.value = basicFormatter.toString()
        }
    }

    Text(
        formattedContent.value,
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, top = 0.dp, end = 16.dp, bottom = 16.dp)
            .verticalScroll(rememberScrollState()),
    )
}
