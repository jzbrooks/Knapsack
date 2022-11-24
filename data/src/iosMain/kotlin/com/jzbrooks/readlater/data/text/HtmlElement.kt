package com.jzbrooks.readlater.data.text

import cocoapods.HTMLReader.HTMLElement
import cocoapods.HTMLReader.HTMLNode

/** Represents a single element in a static document tree. */
internal actual class HtmlElement(private val node: HTMLElement) {
    actual val tagName: String = node.tagName
    actual val text: String? = node.textContent
    actual val parent: HtmlElement? get() = node.parentElement?.let(::HtmlElement)
    actual val children: List<HtmlElement> get() = node.childElementNodes
        .filterIsInstance<HTMLElement>().map(::HtmlElement)

    actual val nextSibling: HtmlElement? = (node.parentElement?.childElementNodes()?.getOrNull(node.parentElement!!.indexOfChild(node).toInt() + 1) as HTMLElement?)?.let(::HtmlElement)
}
