package com.jzbrooks.readlater

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.jzbrooks.readlater.common.Entry
import com.jzbrooks.readlater.common.EntryRepository
import com.jzbrooks.readlater.ui.theme.ReadlaterTheme

@Composable
fun ReadingListScreen(
    repository: EntryRepository,
    onEntryClicked: (Entry) -> Unit,
) {
    LaunchedEffect(null) {
        repository.updateEntries()
    }

    ReadingList(repository, onEntryClicked)
}

@Composable
fun ReadingList(
    repository: EntryRepository,
    onEntryClicked: (Entry) -> Unit,
) {
    val entries = repository.entries.collectAsState(initial = emptyList())

    LazyColumn {
        items(entries.value, key = com.jzbrooks.readlater.common.Entry::id) {
            ReadingListEntry(it, Modifier.padding(8.dp).clickable { onEntryClicked(it) })
            Divider()
        }
    }
}

@Composable
fun ReadingListEntry(entry: Entry, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth().fillMaxHeight(2 / 16f),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(Modifier.size(48.dp), contentAlignment = Alignment.Center) {
            if (entry.preview_picture != null) {
                AsyncImage(
                    model = entry.preview_picture!!.replace("http://", "https://"),
                    contentDescription = null,
                    onError = { Log.e("coil", "Image failed ${it.result.throwable}") },
                    contentScale = ContentScale.FillHeight,
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_background),
                    contentDescription = null,
                )
            }
        }

        Spacer(Modifier.size(8.dp))

        Text(entry.title, style = MaterialTheme.typography.h3)
    }
}

@Preview(showBackground = true)
@Composable
fun ReadingListEntryPreview() {
    ReadlaterTheme {
        ReadingListEntry(
            Entry(
                1,
                "You should read this",
                "https://example.com/image.jpeg",
                "<h1>dude</h1>",
                null,
            ),
        )
    }
}
