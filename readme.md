# Knapsack

A wallabag client

[![Build Android](https://github.com/jzbrooks/Knapsack/actions/workflows/ci-android.yml/badge.svg)](https://github.com/jzbrooks/Knapsack/actions/workflows/ci-android.yml)
[![Build iOS](https://github.com/jzbrooks/Knapsack/actions/workflows/ci-ios.yml/badge.svg)](https://github.com/jzbrooks/Knapsack/actions/workflows/ci-ios.yml)

## Building
In general, each platform builds like typical projects with standard toolchains. The common library is a Gradle-based build.

### iOS
Requires Xcode. Since the project uses CocoaPods, you'll need to open `knapsack-ios/readlater.xcworkspace`. The `readlater` scheme will build the app and the common library if required.

### Android
It is a typical gradle build. `./gradlew :knapsack-android:assembleDebug` will build a debug APK. Running from Android Studio with the default run configuration should work also.

### Common
It is a typical gradle build. If you'd like to build only the common library, you can use `./gradlew :common:tasks` will show you possible tasks. Some may require an Xcode installation.

## Organization

`common/` - a Kotlin Multiplatform library responsible for authentication, web requests, a database, and html-based text rendering

`knapsack-ios/` - a SwiftUI-based iOS wallabag client

`knapsack-android/` - a Jetpack Compose-based Android wallabag client

`knapsack-web/` - tbd
