plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
rootProject.name = "com.sushkpavel.ktor-leetcode"
include("compiler")
include("checker")
include("api")
