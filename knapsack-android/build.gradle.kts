plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose")
}

android {
    namespace = "com.jzbrooks.readlater"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.jzbrooks.readlater"
        minSdk = 28
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        // A developer shortcut for signing in.
        buildConfigField(
            "String",
            "CONST_CLIENT_ID",
            '\"' + (properties["CONST_CLIENT_ID"]?.toString() ?: "") + '\"',
        )
        buildConfigField(
            "String",
            "CONST_CLIENT_SECRET",
            '\"' + (properties["CONST_CLIENT_SECRET"]?.toString() ?: "") + '\"',
        )
        buildConfigField(
            "String",
            "CONST_USERNAME",
            '\"' + (properties["CONST_USERNAME"]?.toString() ?: "") + '\"',
        )
        buildConfigField(
            "String",
            "CONST_PASSWORD",
            '\"' + (properties["CONST_PASSWORD"]?.toString() ?: "") + '\"',
        )
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles("proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }

    packagingOptions {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }

    lint {
        checkDependencies = true
        warningsAsErrors = true
        informational += setOf("GradleDependency")
        targetSdk = 34
    }
}

dependencies {

    implementation(project(":common"))
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.4")
    implementation("androidx.activity:activity-compose:1.9.1")
    implementation("androidx.compose.ui:ui:1.6.8")
    implementation("androidx.compose.ui:ui-tooling-preview:1.6.8")
    implementation("androidx.compose.material:material:1.6.8")
    implementation("androidx.navigation:navigation-compose:2.7.7")

    implementation("io.coil-kt:coil-compose:2.7.0")

    testImplementation("junit:junit:4.13.2")

    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.6.8")

    debugImplementation("androidx.compose.ui:ui-tooling:1.6.8")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.6.8")
}
