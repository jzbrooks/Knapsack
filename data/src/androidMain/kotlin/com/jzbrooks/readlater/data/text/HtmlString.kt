package com.jzbrooks.readlater.data.text

import android.graphics.Typeface
import android.graphics.fonts.FontStyle
import android.text.Html
import android.text.style.StyleSpan
import androidx.core.text.getSpans
import org.jsoup.Jsoup
import org.jsoup.select.NodeTraversor

// todo: handle basic style markup
actual fun HtmlString.toStyledString(): StyledString {
    val text = Html.fromHtml(toString(), Html.FROM_HTML_MODE_LEGACY)
    val spans = text.getSpans<Any>(0, text.length)

    val boldSpans = spans.filterIsInstance<StyleSpan>()
        .filter { it.style == Typeface.BOLD }
        .map { text.getSpanStart(it)..text.getSpanEnd(it) }

    return StyledString(text.toString(), boldPositions = boldSpans)
}
