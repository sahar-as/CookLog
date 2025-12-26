package utility

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

internal fun Project.configureKotlinAndroid(commonExtension: CommonExtension<*, *, *, *, *, *>) {
    commonExtension.apply {
        compileSdk = Integer.parseInt(libs.findVersion("android-compileSdk").get().toString())
        defaultConfig {
            minSdk = Integer.parseInt(libs.findVersion("android-minSdk").get().toString())
            vectorDrawables.useSupportLibrary = true
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
            isCoreLibraryDesugaringEnabled = true
        }
    }
    tasks.withType<KotlinCompile>().configureEach {

        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }
}