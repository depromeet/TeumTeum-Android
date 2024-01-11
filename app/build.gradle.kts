plugins {
    id("teumteum.android.application")
    id("teumteum.android.androidHilt")
    alias(libs.plugins.androidKotlin)
}
dependencies {
    implementation(project(":core:base"))
    implementation(project(":core:data"))
    implementation(project(":core:domain"))
    implementation(libs.androidx.ui.text.android)
}
