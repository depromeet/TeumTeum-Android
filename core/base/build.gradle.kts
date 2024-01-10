plugins {
    id("teumteum.android.library")
    alias(libs.plugins.androidKotlin)
}

android {
    buildFeatures {
        dataBinding = true
        viewBinding = true
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.6"
    }

    namespace = "com.teumteum.base"
}

dependencies {
    // Lifecycle Ktx
    implementation(libs.androidx.lifeCycleKtx)
    implementation(libs.androidx.appCompat)
    implementation(libs.materialDesign)
    implementation(libs.androidx.constraintLayout)
    implementation(libs.bundles.compose)
}
