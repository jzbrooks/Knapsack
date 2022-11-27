plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.jzbrooks.readlater"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.jzbrooks.readlater"
        minSdk = 28
        targetSdk = 33
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.2"
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
    }
}

dependencies {

    implementation(project(":common"))
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.5.1")
    implementation("androidx.activity:activity-compose:1.6.1")
    implementation("androidx.compose.ui:ui:1.2.1")
    implementation("androidx.compose.ui:ui-tooling-preview:1.2.1")
    implementation("androidx.compose.material:material:1.2.1")
    implementation("androidx.navigation:navigation-compose:2.5.3")

    implementation("io.coil-kt:coil:2.2.2")
    implementation("io.coil-kt:coil-compose:2.2.2")

    testImplementation("junit:junit:4.13.2")

    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.2.1")

    debugImplementation("androidx.compose.ui:ui-tooling:1.2.1")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.2.1")
}
