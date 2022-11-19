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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.os.bundleOf
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination
import com.jzbrooks.readlater.data.Entry
import com.jzbrooks.readlater.data.EntryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
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

@Preview(showBackground = true)
@Composable
fun Preview() {
    val entry = NavBackStackEntry.create(
        null,
        NavDestination("dummy"),
        arguments = bundleOf("id" to 1)
    )

    val repository = object : EntryRepository {
        val article = Entry(
            1,
            "example",
            "https://example.com",
            "<p>This is <b>big</b></p>",
            null,
        )

        override val entries: Flow<List<Entry>>
            get() = flow {
                emit(listOf(article))
            }

        override suspend fun updateEntries() { }
        override suspend fun deleteAllEntries() { }

        override suspend fun getEntry(id: Long): Entry? {
            return if (id == 1L) article else null
        }
    }

    ReadingScreen(entry, repository)
}
