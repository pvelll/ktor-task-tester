plugins {
    id("java-library")
    alias(libs.plugins.kotlin.jvm)
    id("org.jetbrains.kotlin.plugin.serialization") version "2.0.20"
}

group = "com.sushkpavel.tasktester"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.kotlinx.datetime)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.exposed.core)
    implementation(libs.exposed.jdbc)
    implementation(libs.exposed.java.time)
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}