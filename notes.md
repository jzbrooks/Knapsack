## Justin

1. NSAttributedString doesn't conform to CharSequence, which is annoying
2. SQLDelight makes custom properties trickier
3. An `HtmlString.toStyledString` that can be called from common main is difficult because of #1. So we _must_ delegate the conversion to a platform-specific styled string to the library consumers.
4. CocoaPods is the only reasonable way to deal with swift dependencies. Some have aversions to it.
5. iOS library dependencies in a multiplatform module only really work if the library is objective-c compatible. Some libraries like SwiftSoup are pure Swift with no intention of adding objective-c compatibility. This seems like a step backward. https://youtrack.jetbrains.com/issue/KT-49132/Support-importing-Swift-declarations-without-objc-to-Kotlin
6. common module weirdness (like no vararg apis (like string formatting))
7. You have to annotate every declaration you would like to see in the JS library artifact with `@JsExport`. https://youtrack.jetbrains.com/issue/KT-47200/KJS-IR-Define-exports-without-JsExport
8. JsExport doesn't support a bunch of basic types (like unsigned integral types (and Long), common collection interfaces like List<T>, etc)
9. JsExport doesn't support suspend functions, which are a natural API for kotlin (and could be mapped into promises). This is annoying for many popular libraries like ktor who themselves use suspend functions. There isn't a great way to call them in common code.
10. Since @JsExport can only target top-level members, adding a method to a class specifically for JS that shares other logic in the class is impossible. The only recourse is an extension method or top level function with parameters for all of the internal state of the initial class.
11. JsExport restrictions are out of control. Nested classes and interfaces aren't supported.
