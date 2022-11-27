package com.jzbrooks.readlater.data.text

import org.jsoup.Jsoup
import org.jsoup.nodes.Node
import org.jsoup.select.NodeTraversor

actual fun HtmlString.toStyledString(): StyledString {
    val document = HtmlElement(Jsoup.parse(toString()).root()).children[0]
    return document.toStyledString()
}
