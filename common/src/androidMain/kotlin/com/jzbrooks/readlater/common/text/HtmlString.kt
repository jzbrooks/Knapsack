package com.jzbrooks.readlater.common.text

import org.jsoup.Jsoup

actual fun HtmlString.toStyledString(): StyledString {
    val document = HtmlElement(Jsoup.parse(toString()).root()).children[0]
    return document.toStyledString()
}
