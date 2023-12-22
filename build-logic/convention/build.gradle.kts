plugins {
    `kotlin-dsl`
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    compileOnly(libs.android.build)
    compileOnly(libs.kotlin.gradle)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "teumteum.android.application"
            implementationClass = "plugins.AndroidApplicationPlugin"
        }
        register("androidLibrary") {
            id = "teumteum.android.library"
            implementationClass = "plugins.AndroidLibraryPlugin"
        }
        register("androidHilt") {
            id = "teumteum.android.androidHilt"
            implementationClass = "plugins.AndroidHiltPlugin"
        }
        register("androidKotlin") {
            id = "teumteum.android.kotlin"
            implementationClass = "plugins.AndroidKotlinPlugin"
        }
    }
}
