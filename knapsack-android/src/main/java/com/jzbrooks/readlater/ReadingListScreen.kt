package com.jzbrooks.readlater

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.jzbrooks.readlater.data.Entry
import com.jzbrooks.readlater.data.EntryRepository
import com.jzbrooks.readlater.ui.theme.ReadlaterTheme

@Composable
fun ReadingListScreen(
    repository: EntryRepository,
    onEntryClicked: (Entry) -> Unit,
) {
    val entries = repository.entries.collectAsState(initial = emptyList())

    LazyColumn {
        items(entries.value, key = Entry::id) {
            ReadingEntry(it, Modifier.padding(8.dp).clickable { onEntryClicked(it) })
            Divider()
        }
    }
}

@Composable
fun ReadingEntry(entry: Entry, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth().fillMaxHeight(2/16f),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(Modifier.size(48.dp), contentAlignment = Alignment.Center) {
            if (entry.preview_picture != null) {
                AsyncImage(
                    model = entry.preview_picture!!.replace("http://", "https://"),
                    contentDescription = null,
                    onError = { Log.e("coil", "Image failed ${it.result.throwable}") },
                    contentScale = ContentScale.FillHeight
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_background),
                    contentDescription = null
                )
            }
        }

        Spacer(Modifier.size(8.dp))

        Text(entry.title, style = MaterialTheme.typography.h3)
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ReadlaterTheme {
        ReadingEntry(
            Entry(
                1,
                "You should read this",
                "https://americanreformer.org/wp-content/uploads/2022/10/mai-rodriguez-__4mdndKK1w-unsplash-1-scaled-e1664893436482-1536x476.jpeg",
                "<h1>dude</h1>",
                null
            )
        )
    }
}
