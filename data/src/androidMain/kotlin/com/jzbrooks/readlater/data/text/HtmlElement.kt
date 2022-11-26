package com.jzbrooks.readlater.data.text

import org.jsoup.nodes.Node
import org.jsoup.nodes.TextNode

/** Represents a single element in a static document tree. */
internal actual class HtmlElement(private val node: Node) {
    actual val tagName: String
        get() = node.nodeName()
    actual val text: String?
        get() = (node as? TextNode)?.text()?.trim('\r', '\n')
    actual val parent: HtmlElement?
        get() = node.parentNode()?.let(::HtmlElement)
    actual val children: List<HtmlElement>
        get() = node.childNodes().map(::HtmlElement)
    actual val nextSibling: HtmlElement?
        get() = node.nextSibling()?.let(::HtmlElement)
}
