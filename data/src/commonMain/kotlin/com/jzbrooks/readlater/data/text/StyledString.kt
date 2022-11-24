package com.jzbrooks.readlater.data.text

data class StyledString(
    val text: String,
    val boldPositions: List<IntRange> = emptyList(),
    val italicPositions: List<IntRange> = emptyList(),
    val underlinedPositions: List<IntRange> = emptyList(),
)

fun HtmlElement.toStyledString(): StyledString {
    TODO("implement walking dom")
}
