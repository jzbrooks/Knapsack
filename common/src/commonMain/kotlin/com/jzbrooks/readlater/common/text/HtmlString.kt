package com.jzbrooks.readlater.common.text

class HtmlString(private val text: CharSequence): CharSequence by (text) {
    override fun toString() = text.toString()
}

expect fun HtmlString.toStyledString(): StyledString
