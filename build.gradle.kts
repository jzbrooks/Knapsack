import com.diffplug.gradle.spotless.SpotlessExtension

plugins {
    id("com.android.library") version "8.4.1" apply false
    id("com.android.application") version "8.4.1" apply false
    id("org.jetbrains.kotlin.multiplatform") version "2.0.0" apply false
    id("org.jetbrains.kotlin.plugin.serialization") version "2.0.0" apply false
    id("org.jetbrains.kotlin.android") version "2.0.0" apply false
    id("org.jetbrains.kotlin.native.cocoapods") version "2.0.0" apply false
    id("com.diffplug.spotless") version "6.13.0"
}

buildscript {
    repositories { mavenCentral() }
    dependencies {
        classpath("com.squareup.sqldelight:gradle-plugin:1.5.5")
    }
}

tasks {
    named("clean", Delete::class) {
        delete(rootProject.buildDir)
    }
}

configure<SpotlessExtension> {
    kotlin {
        target("**/*.kt")
        ktlint("0.48.2")
    }

    kotlinGradle {
        target("**/*.kts")
        ktlint("0.48.2")
    }
}
