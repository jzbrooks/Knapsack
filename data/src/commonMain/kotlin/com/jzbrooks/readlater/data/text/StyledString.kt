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
    var depth = 0

    while (node != null) {
        val parent: HtmlElement? = node.parent

        visitor.head(node) // visit current node

        if (!parent?.children.isNullOrEmpty()) { // descend
            node = node.children[0]
            depth++
        } else {
            while (true) {
                checkNotNull(node)
                if (!(node.nextSibling == null && depth > 0)) break
                visitor.tail(node) // when no more siblings, ascend
                node = node.parent
                depth--
            }

            if (node != null) {
                visitor.tail(node)
            }

            if (node === this) break
            node = node?.nextSibling
        }
    }

    return StyledString(visitor.text)
}
