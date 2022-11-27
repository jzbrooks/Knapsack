package com.jzbrooks.readlater.common.text

import cocoapods.HTMLReader.*

/** Represents a single element in a static document tree. */
internal actual class HtmlElement(private val node: HTMLNode) {
    actual val tagName: String = when (node) {
        is HTMLDocument -> "#document"
        is HTMLDocumentType -> "#doctype"
        is HTMLTextNode -> "#text"
        is HTMLComment -> "#comment"
        is HTMLElement -> node.tagName
        else -> "#unknown"
    }

    actual val text: String?
        get() = (node as? HTMLTextNode)?.data?.trim('\r', '\n')

    actual val parent: HtmlElement?
        get() = node.parentElement?.let(::HtmlElement)

    actual val children: List<HtmlElement>
        get() {
            val children = mutableListOf<HtmlElement>()
            for (i in 0UL until node.children.count) {
                children.add(HtmlElement(node.childAtIndex(i)))
            }
            return children
        }

    actual val nextSibling: HtmlElement?
        get() {
            val parent = node.parentElement ?: return null

            val nextIndex = parent.indexOfChild(node) + 1UL
            if (nextIndex >= parent.children.count) {
                return null
            }

            return HtmlElement(parent.childAtIndex(nextIndex))
        }
}
