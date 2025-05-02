plugins {
    alias(libs.plugins.kotlin.jvm)
    id("java-library")
    id("org.jetbrains.kotlin.plugin.serialization") version "2.0.20"
}


group = "com.sushkpavel.tasktester"
version = "unspecified"

repositories {
    mavenCentral()
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17
    }
}

dependencies {
    implementation(libs.ktor.server.auth)
    implementation(libs.ktor.server.auth.jwt)
    implementation(libs.koin.ktor)
    implementation(libs.koin.logger.slf4j)
    implementation(libs.ktor.server.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.network.tls.certificates)
    implementation(libs.mysql.connector.java)
    implementation(libs.ktor.server.netty)
    implementation(libs.logback.classic)
    implementation(libs.ktor.server.config.yaml)
    testImplementation(kotlin("test"))
    implementation(project(":data"))}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}