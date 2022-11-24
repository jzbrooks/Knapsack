package com.jzbrooks.readlater.data.text

import cocoapods.HTMLReader.HTMLDocument

// todo: parse html with swiftsoup?
fun HtmlString.toStyledString(): StyledString {
    val document = HTMLDocument(toString()).rootElement?.let(::HtmlElement)
    return document!!.toStyledString()
}
