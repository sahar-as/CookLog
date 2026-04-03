import org.gradle.kotlin.dsl.implementation
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `kotlin-dsl`
}

group = "com.saharapps.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.gradle.plugin.compose)
    compileOnly(libs.gradle.plugin.compose.compiler)

    implementation(libs.gradle.plugin.serialization)
    compileOnly(libs.gradle.plugin.ksp)
    compileOnly(libs.gradle.plugin.room)
}

gradlePlugin {
    plugins {
        register( "androidApplication"){
            id = "cooklog.android.application"
            implementationClass = "AndroidMainGradleConventionPlugin"
        }

        register("AndroidLibrary"){
            id = "cooklog.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }

        register("kmpConfig") {
            id = "cooklog.kmp.gradle"
            implementationClass = "KmpConventionPlugin"
        }

        register("CommonCompose"){
            id = "cooklog.kmp.commoncompose"
            implementationClass = "CommonComposeConventionPlugin"
        }
        register("Room"){
            id = "cooklog.kmp.room"
            implementationClass = "RoomConventionPlugin"
        }
    }
}