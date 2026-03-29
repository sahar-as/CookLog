plugins {
    id("cooklog.android.application")
    id("cooklog.kmp.commoncompose")
}

kotlin {
    initializeAndroidTarget()
    initializeFramework("ComposeApp")

    sourceSets {
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
        }
        commonMain.dependencies {
            libs.apply {
                implementation(androidx.lifecycle.viewmodelCompose)
                implementation(androidx.lifecycle.runtimeCompose)
                implementation(androidx.navigation)
            }

            projects.apply {
                implementation(core.navigation)
                implementation(feature.catalog)
                implementation(feature.recipe)
            }
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

android {
    namespace = "com.saharapps.cooklog"
}
