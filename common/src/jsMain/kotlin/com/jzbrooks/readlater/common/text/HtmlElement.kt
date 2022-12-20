package com.jzbrooks.readlater.common.text

/** Represents a single element in a static document tree. */
internal actual class HtmlElement {
    actual val tagName: String
        get() = TODO("Not yet implemented")
    actual val text: String?
        get() = TODO("Not yet implemented")
    actual val parent: HtmlElement?
        get() = TODO("Not yet implemented")
    actual val children: List<HtmlElement>
        get() = TODO("Not yet implemented")
    actual val nextSibling: HtmlElement?
        get() = TODO("Not yet implemented")
}
