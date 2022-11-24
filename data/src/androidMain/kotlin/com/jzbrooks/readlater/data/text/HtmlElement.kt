package com.jzbrooks.readlater.data.text

import org.jsoup.nodes.Node
import org.jsoup.nodes.TextNode

/** Represents a single element in a static document tree. */
internal actual class HtmlElement(private val node: Node) {
    actual val tagName: String = node.nodeName()
    actual val text: String? = (node as? TextNode)?.text()
    actual val parent: HtmlElement? get() = node.parentNode()?.let(::HtmlElement)
    actual val children: List<HtmlElement> get() = node.childNodes().map(::HtmlElement) ?: emptyList()
    actual val nextSibling: HtmlElement? = node.nextSibling()?.let(::HtmlElement)
}
