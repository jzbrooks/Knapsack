## Justin

1. NSAttributedString doesn't conform to CharSequence, which is annoying
2. SQLDelight makes custom properties trickier
3. An `HtmlString.toStyledString` that can be called from common main is difficult because of #1. So we _must_ delegate the conversion to a platform-specific styled string to the library consumers.
4. CocoaPods is the only reasonable way to deal with swift dependencies. Some have aversions to it.
5. iOS library dependencies in a multiplatform module only really work if the library is objective-c compatible. Some libraries like SwiftSoup are pure Swift with no intention of adding objective-c compatibility. This seems like a step backward. https://youtrack.jetbrains.com/issue/KT-49132/Support-importing-Swift-declarations-without-objc-to-Kotlin
6. common module weirdness (like no vararg apis (like string formatting))
7. You have to annotate every declaration you would like to see in the JS library artifact with `@JsExport`. https://youtrack.jetbrains.com/issue/KT-47200/KJS-IR-Define-exports-without-JsExport
