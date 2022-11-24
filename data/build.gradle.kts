import org.jetbrains.kotlin.gradle.plugin.mpp.BitcodeEmbeddingMode.BITCODE

plugins {
    id("org.jetbrains.kotlin.multiplatform")
    id("org.jetbrains.kotlin.plugin.serialization")
    id("org.jetbrains.kotlin.native.cocoapods")
    id("com.android.library")
    id("com.squareup.sqldelight")
}

kotlin {
    android()
    ios()

    cocoapods {
        version = "1.0"
        summary = "Some description for a Kotlin/Native module"
        homepage = "Link to a Kotlin/Native module homepage"

        framework {
            baseName = "data"
            ios.deploymentTarget = "15"
        }

        pod("GONMarkupParser")
        pod("GDataXML-HTML") {
            version = "~> 1.4.1"
        }
    }

    sourceSets {
        val commonMain by getting {
            kotlin.srcDir("src/generated/kotlin")

            dependencies {
                // probably hide this?
                api("com.russhwolf:multiplatform-settings-no-arg:1.0.0-RC")

                implementation("io.ktor:ktor-client-core:2.1.2")
                implementation("io.ktor:ktor-client-content-negotiation:2.1.2")
                implementation("io.ktor:ktor-serialization-kotlinx-json:2.1.2")
                implementation("com.squareup.sqldelight:coroutines-extensions:1.5.3")
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }

        val androidMain by getting {
            dependencies {
                implementation("com.squareup.sqldelight:android-driver:1.5.3")
                implementation("io.ktor:ktor-client-android:2.1.2")
                implementation("org.jsoup:jsoup:1.15.3")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")
            }
        }

        val androidTest by getting
        val iosMain by getting {
            dependsOn(commonMain)
            dependencies {
                implementation("com.squareup.sqldelight:native-driver:1.5.3")
                implementation("io.ktor:ktor-client-ios:2.1.2")
            }
        }
        val iosTest by getting {
            dependsOn(commonTest)
        }
    }
}

android {
    namespace = "com.jzbrooks.readlater.data"
    compileSdk = 33
    defaultConfig {
        minSdk = 28
        targetSdk = 33
    }

    lint {
        informational += setOf("GradleDependency")
    }
}

sqldelight {
    database("Database") {
        packageName = "com.jzbrooks.readlater.data"
    }
}
