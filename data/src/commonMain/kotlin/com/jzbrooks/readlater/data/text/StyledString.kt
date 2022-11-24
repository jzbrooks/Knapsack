package com.jzbrooks.readlater.data.text

data class StyledString(
    val text: String,
    val boldPositions: List<IntRange> = emptyList(),
    val italicPositions: List<IntRange> = emptyList(),
    val underlinedPositions: List<IntRange> = emptyList(),
)

internal fun HtmlElement.toStyledString(): StyledString {
    val visitor = FormattingVisitor()
    var node: HtmlElement? = this

    while (node != null) {
        visitor.head(node)

        // Iterative DFS
        if (node.children.isNotEmpty()) {
            node = node.children[0]
        } else {
            while (node != null && node.nextSibling == null) {
                visitor.tail(node) // when no more siblings, ascend
                node = node.parent
            }

            if (node != null) {
                visitor.tail(node)
            }

            if (node === this) break

            node = node?.nextSibling
        }
    }

    return StyledString(visitor.text, visitor.boldPositions)
}
