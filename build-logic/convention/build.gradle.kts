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
}

gradlePlugin {
    plugins {
//        register( "androidApplication"){
//            id = "cooklog.android.application"
//            implementationClass = "AndroidApplicationConventionPlugin"
//        }
//
//        register("androidLibrary"){
//            id = "cooklog.android.library"
//        }
    }
}