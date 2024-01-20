plugins {
    id("java-library")
    kotlin("jvm")
    kotlin("kapt")
    id ("org.jetbrains.kotlin.plugin.serialization") version "1.6.10"

}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(libs.bundles.kotlin)
    implementation(libs.hiltCore)
    kapt(libs.hiltAndroidCompiler)
}
