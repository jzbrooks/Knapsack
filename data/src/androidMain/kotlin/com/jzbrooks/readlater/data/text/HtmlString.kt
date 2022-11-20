package com.jzbrooks.readlater.data.text

import org.jsoup.Jsoup
import org.jsoup.select.NodeTraversor

// todo: handle basic style markup
actual fun HtmlString.toStyledString(): StyledString {
    val basicFormatter = FormattingVisitor()
    NodeTraversor.traverse(basicFormatter, Jsoup.parse(toString()).root())
    return StyledString(basicFormatter.toString())
}
