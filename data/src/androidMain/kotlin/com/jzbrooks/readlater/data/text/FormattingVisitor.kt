package com.jzbrooks.readlater.data.text

import org.jsoup.nodes.Node
import org.jsoup.nodes.TextNode
import org.jsoup.select.NodeVisitor

class FormattingVisitor : NodeVisitor {
    private val builder = StringBuilder()

    override fun head(node: Node, depth: Int) {
        val name = node.nodeName()

        // TextNodes carry all user-readable text in the DOM.
        if (node is TextNode) append(node.text())
        else if (name == "li") append("\n * ")
        else if (name == "dt") append("  ")
        else if (name in setOf(
                "p",
                "h1",
                "h2",
                "h3",
                "h4",
                "h5",
                "tr",
            )
        ) append("\n")
    }

    override fun tail(node: Node, depth: Int) {
        val name = node.nodeName()
        if (name in setOf(
                "br",
                "dd",
                "dt",
                "p",
                "h1",
                "h2",
                "h3",
                "h4",
                "h5",
            )
        ) append("\n")
    }

    private fun append(text: String) {
        if (text == " " &&
            (builder.isEmpty() || builder.substring(builder.length - 1) in setOf(" ", "\n"))
        ) return  // don't accumulate long runs of empty spaces

        builder.append(text)
    }

    override fun toString() = builder.toString()
}

