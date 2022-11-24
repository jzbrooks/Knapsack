package com.jzbrooks.readlater.data.text

import org.jsoup.Jsoup
import org.jsoup.nodes.Node
import org.jsoup.select.NodeTraversor

// todo: handle basic style markup
actual fun HtmlString.toStyledString(): StyledString {
    val document = HtmlElement(Jsoup.parse(toString()).root())
    return document.toStyledString()
}
