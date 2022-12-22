import com.diffplug.gradle.spotless.SpotlessExtension

plugins {
    id("com.android.library") version "7.3.1" apply false
    id("com.android.application") version "7.3.1" apply false
    id("org.jetbrains.kotlin.multiplatform") version "1.7.21" apply false
    id("org.jetbrains.kotlin.plugin.serialization") version "1.7.21" apply false
    id("org.jetbrains.kotlin.android") version "1.7.21" apply false
    id("org.jetbrains.kotlin.native.cocoapods") version "1.7.21" apply false
    id("com.diffplug.spotless") version "6.12.0"
}

buildscript {
    repositories { mavenCentral() }
    dependencies {
        classpath("com.squareup.sqldelight:gradle-plugin:1.5.4")
    }
}

tasks {
    named("clean", Delete::class) {
        delete(rootProject.buildDir)
    }
}

configure<SpotlessExtension> {
    // Spotless doesn't respect .editorconfig yet.
    // https://github.com/diffplug/spotless/issues/142
    val editorConfigProperties = mapOf(
        "charset" to "utf-8",
        "end_of_line" to "lf",
        "trim_trailing_whitespace" to true,
        "insert_final_newline" to true,
        "indent_style" to "space",
        "indent_size" to 4,
        "max_line_length" to 100,
        "ij_kotlin_allow_trailing_comma" to true,
        "ij_kotlin_allow_trailing_comma_on_call_site" to true,
    )

    kotlin {
        target("**/*.kt")
        ktlint("0.47.1").editorConfigOverride(editorConfigProperties)
    }

    kotlinGradle {
        target("**/*.kts")
        ktlint("0.47.1").editorConfigOverride(editorConfigProperties)
    }
}
