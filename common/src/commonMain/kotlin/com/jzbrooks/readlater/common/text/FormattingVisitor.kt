package com.jzbrooks.readlater.common.text

internal class FormattingVisitor {
    private val builder = StringBuilder(1024 * 1024)
    val text: String
        get() = builder.toString()

    private var boldStart: Int? = null
    val boldPositions = mutableListOf<IntRange>()

    private var italicStart: Int? = null
    val italicPositions = mutableListOf<IntRange>()

    private var underlineStart: Int? = null
    val underlinePositions = mutableListOf<IntRange>()

    fun head(element: HtmlElement) {
        val name = element.tagName
        val text = element.text

        if (text != null) {
            append(text)
        } else if (name == "strong" || name == "b") {
            boldStart = builder.length
        } else if (name == "em" || name == "i") {
            italicStart = builder.length
        } else if (name == "u") {
            underlineStart = builder.length
        } else if (name == "li") {
            append("\n * ")
        } else if (name == "dt") {
            append("  ")
        } else if (builder.isNotEmpty() && // Don't prepend an newline
            name in setOf(
                "p",
                "h1",
                "h2",
                "h3",
                "h4",
                "h5",
                "tr",
            )
        ) {
            append("\n")
        }
    }

    fun tail(element: HtmlElement) {
        when (element.tagName) {
            "br", "dd", "dt", "p", "h1", "h2", "h3", "h4", "h5" -> {
                append("\n")
            }
            "strong", "b" -> {
                val range = boldStart!! until builder.length
                boldStart = null
                boldPositions.add(range)
            }
            "em", "i" -> {
                val range = italicStart!! until builder.length
                italicStart = null
                italicPositions.add(range)
            }
            "u" -> {
                val range = underlineStart!! until builder.length
                underlineStart = null
                underlinePositions.add(range)
            }
        }
    }

    private fun append(text: String) {
        if (text == " " &&
            (builder.isEmpty() || builder.last() in setOf(' ', '\n'))
        ) {
            return // don't accumulate long runs of empty spaces
        }

        builder.append(text)
    }
}
