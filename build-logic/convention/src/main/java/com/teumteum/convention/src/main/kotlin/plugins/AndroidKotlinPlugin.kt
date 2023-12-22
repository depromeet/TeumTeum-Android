package plugins

import com.teumteum.convention.src.main.kotlin.ext.getBundle
import com.teumteum.convention.src.main.kotlin.ext.getVersionCatalog
import com.teumteum.convention.src.main.kotlin.ext.implementation
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

/**
 * kotlin 를 적용할 모듈에 사용할 플러그인
 *
 * plugin id : [teumteum.android.kotlin]
 */
class AndroidKotlinPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("kotlin-android")
        }

        val libs = extensions.getVersionCatalog()
        dependencies {
            implementation(libs.getBundle("kotlin"))
        }
    }
}
