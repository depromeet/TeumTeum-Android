package plugins

import Constants
import com.android.build.gradle.LibraryExtension
import com.teumteum.convention.src.main.kotlin.ext.androidTestImplementation
import com.teumteum.convention.src.main.kotlin.ext.getLibrary
import com.teumteum.convention.src.main.kotlin.ext.getVersionCatalog
import com.teumteum.convention.src.main.kotlin.ext.implementation
import com.teumteum.convention.src.main.kotlin.ext.kapt
import com.teumteum.convention.src.main.kotlin.ext.testImplementation
import ext.application.kotlinOptions
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

/**
 * Android Library 모듈에 적용할 Plugin
 *
 * plugin id : [teumteum.android.library]
 */
class AndroidLibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
            }

            extensions.configure<LibraryExtension> {
                configureAndroidCommonPlugin()

                compileSdk = Constants.compileSdk
                defaultConfig {
                    minSdk = Constants.minSdk

                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                    consumerProguardFiles("consumer-rules.pro")
                }
                compileOptions {
                    sourceCompatibility = JavaVersion.VERSION_17
                    targetCompatibility = JavaVersion.VERSION_17
                }

                kotlinOptions {
                    jvmTarget = Constants.jvmVersion
                }
            }

            val libs = extensions.getVersionCatalog()
            dependencies {
                // test
                testImplementation(libs.getLibrary("jUnit"))
                androidTestImplementation(libs.getLibrary("androidTest"))
                androidTestImplementation(libs.getLibrary("espresso"))

                // hilt
                implementation(libs.getLibrary("hilt"))
                kapt(libs.getLibrary("hiltAndroidCompiler"))
            }
        }
    }
}
