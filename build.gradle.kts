plugins {
    kotlin("jvm") version "2.0.20" apply false
}

subprojects {
    apply(plugin = "kotlin")

    repositories {
        mavenCentral()
    }
}