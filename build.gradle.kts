plugins {
    kotlin("jvm") version "2.0.20"
    application
}

group = "com.kotlinassistant"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    // Kotlin standard library
    implementation(kotlin("stdlib"))
    
    // Coroutines for async operations
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    
    // HTTP client for AI API calls
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    
    // JSON parsing
    implementation("com.google.code.gson:gson:2.10.1")
    
    // CLI support
    implementation("com.github.ajalt.clikt:clikt:4.2.1")
    
    // Testing
    testImplementation(kotlin("test"))
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    testImplementation("io.mockk:mockk:1.13.8")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(11)
}

application {
    mainClass.set("com.kotlinassistant.MainKt")
}
