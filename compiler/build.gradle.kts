import com.google.protobuf.gradle.id

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.ktor)
    id("com.google.protobuf") version "0.9.4"
    id("org.jetbrains.kotlin.plugin.serialization") version "2.0.20"
}

group = "com.sushkpavel"
version = "0.0.1"

repositories {
    mavenCentral()
    google()
}


dependencies {
    implementation(libs.kotlinx.coroutines)
    implementation(libs.kotlinx.serialization)
    implementation(libs.logback.classic)
    implementation(libs.grpc.netty.shaded)
    implementation(libs.grpc.stub)
    implementation(libs.grpc.protobuf)
    implementation(libs.protobuf.kotlin)
    implementation(libs.grpc.kotlin.stub)
}


protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:4.28.3"
    }
    plugins {
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:1.68.0"
        }
//        id("grpckt") {
//            artifact = "io.grpc:protoc-gen-grpc-kotlin-1.4.0-jdk8.jar"
//        }
    }
    generateProtoTasks {
        all().forEach { task ->
            task.plugins {
                id("grpc")
//                id("grpckt")
            }
        }
    }
}

application {
    mainClass.set("Service")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}


dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
