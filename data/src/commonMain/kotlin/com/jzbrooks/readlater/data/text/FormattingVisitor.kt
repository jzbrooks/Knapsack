package com.jzbrooks.readlater.data.text

internal class FormattingVisitor {
    private val builder = StringBuilder(1024*1024)
    val text: String
        get() = builder.toString()

    fun head(element: HtmlElement) {
        val name = element.tagName

        if (element.text != null) append(element.text)
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

    fun tail(element: HtmlElement) {
        val name = element.tagName
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
}
