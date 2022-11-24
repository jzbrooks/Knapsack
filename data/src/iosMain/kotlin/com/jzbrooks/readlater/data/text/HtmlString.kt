package com.jzbrooks.readlater.data.text

import cocoapods.GDataXML_HTML.*
import cocoapods.GONMarkupParser.*
import kotlinx.cinterop.utf8
import platform.Foundation.NSAttributedString
import platform.Foundation.NSAttributedStringEnumerationOptions
import platform.Foundation.NSAttributedStringEnumerationReverse
import platform.Foundation.NSData
import platform.Foundation.NSRangeFromString
import platform.Foundation.NSUTF16StringEncoding
import platform.Foundation.enumerateAttributesInRange
import platform.Foundation.length

// todo: parse html with swiftsoup?
fun HtmlString.toStyledString(): StyledString {
    val string = GONMarkupParser.defaultMarkupParser()?.attributedStringFromString(toString())
    string!!.enumerateAttributesInRange(NSRangeFromString(string.toString()), 0) { a, b, c ->
    }

    return StyledString(toString())
}
