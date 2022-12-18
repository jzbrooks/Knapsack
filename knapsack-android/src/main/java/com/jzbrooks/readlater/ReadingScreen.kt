package com.jzbrooks.readlater

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import com.jzbrooks.readlater.common.EntryRepository
import com.jzbrooks.readlater.common.text.HtmlString
import com.jzbrooks.readlater.common.text.toStyledString

@Composable
fun ReadingScreen(backStackEntry: NavBackStackEntry, repository: EntryRepository) {
    val id = backStackEntry.arguments?.getLong("id")!!
    val formattedContent = remember { mutableStateOf(AnnotatedString("")) }

    LaunchedEffect(id) {
        val entry = repository.getEntry(id)

        if (entry?.content != null) {
            // todo: Ideally the entry would have an HtmlString-typed property
            //  slightly tricky with SQLDelight generation
            val styledString = HtmlString(entry.content).toStyledString()

            val builder = AnnotatedString.Builder()
            builder.append(styledString.text)

            for (bold in styledString.boldPositions) {
                builder.addStyle(SpanStyle(fontWeight = FontWeight.Bold), bold.first, bold.last + 1)
            }

            for (italic in styledString.italicPositions) {
                builder.addStyle(
                    SpanStyle(fontStyle = FontStyle.Italic),
                    italic.first,
                    italic.last + 1,
                )
            }

            for (underline in styledString.underlinedPositions) {
                builder.addStyle(
                    SpanStyle(textDecoration = TextDecoration.Underline),
                    underline.first,
                    underline.last + 1,
                )
            }

            formattedContent.value = builder.toAnnotatedString()
        }
    }

    EntryText(formattedContent.value)
}

@Composable
private fun EntryText(content: AnnotatedString) {
    Text(
        content,
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, top = 0.dp, end = 16.dp, bottom = 16.dp)
            .verticalScroll(rememberScrollState()),
    )
}

@Preview(showBackground = true)
@Composable
private fun EntryTextPreview() {
    EntryText(
        AnnotatedString(
            """
            This hill, though high, I covet to ascend;
            The difficulty will not me offend.
            For I perceive the way to life lies here.
            Come, pluck up, heart; let's neither faint nor fear.
            Better, though difficult, the right way to go,
            Than wrong, though easy, where the end is woe.
            """.trimIndent(),
        ),
    )
}
