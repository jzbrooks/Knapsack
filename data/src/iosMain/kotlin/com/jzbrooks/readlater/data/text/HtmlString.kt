package com.jzbrooks.readlater.data.text

// todo: parse html with swiftsoup?
fun HtmlString.toStyledString(): StyledString {
    return StyledString(toString())
}
