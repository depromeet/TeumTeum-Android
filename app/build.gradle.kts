plugins {
    id("teumteum.android.application")
    id("teumteum.android.androidHilt")
    id("teumteum.android.kotlin")
}
dependencies {
    implementation(project(":core:base"))
    implementation(project(":core:data"))
    implementation(project(":core:domain"))
    implementation(libs.androidx.ui.text.android)
    implementation(libs.androidx.appCompat)
    implementation(libs.materialDesign)
    implementation(libs.androidx.constraintLayout)
}
