pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    plugins {
        id("com.android.application") version "8.5.2" apply false
        kotlin("android") version "2.0.20" apply false
        kotlin("jvm") version "2.0.20" apply false
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "kotlin-code-assistant"

// Include Android app module
// Note: Building the Android app requires Android SDK to be installed locally.
// To build CLI without Android SDK: comment out the line below or build with: ./gradlew :build
// To build Android app: ensure Android SDK is installed and use: ./gradlew :app:assembleDebug
include(":app")

