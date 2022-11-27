package com.jzbrooks.readlater.common.text

import cocoapods.HTMLReader.HTMLDocument

// todo: parse html with swiftsoup?
actual fun HtmlString.toStyledString(): StyledString {
    val document = HTMLDocument(toString()).rootElement?.let(::HtmlElement)
    return document!!.toStyledString()
}
