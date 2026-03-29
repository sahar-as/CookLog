import com.android.build.api.dsl.androidLibrary
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

fun Project.initializeAndroidTarget() {
    extensions.configure<KotlinMultiplatformExtension> {
        if (pluginManager.hasPlugin("com.android.kotlin.multiplatform.library")) {
            androidLibrary {
                compilerOptions { jvmTarget.set(JvmTarget.JVM_17) }
            }
        } else {
            androidTarget {
                compilerOptions { jvmTarget.set(JvmTarget.JVM_17) }
            }
        }
    }
}

fun Project.initializeFramework(name: String) {
    extensions.configure<KotlinMultiplatformExtension> {
        listOf(
            iosX64(),
            iosArm64(),
            iosSimulatorArm64(),
        ).forEach { iosTarget ->
            iosTarget.binaries.framework {
                baseName = name
                isStatic = true
                linkerOpts.add("-lsqlite3")
            }
        }
    }
}

fun Project.initializeFramework() {
    initializeFramework(project.name)
}
