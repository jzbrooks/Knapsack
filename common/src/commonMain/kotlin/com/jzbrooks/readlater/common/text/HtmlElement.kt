package com.jzbrooks.readlater.common.text

/** Represents a single element in a static document tree. */
internal expect class HtmlElement {
    val tagName: String
    val text: String?
    val parent: HtmlElement?
    val children: List<HtmlElement>
    val nextSibling: HtmlElement?
}
