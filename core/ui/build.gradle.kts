plugins {
    id("cooklog.android.library")
    id("cooklog.kmp.commoncompose")
}

kotlin {
    initializeAndroidTarget()
    initializeFramework()

    sourceSets {
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
        }
        commonMain.dependencies {
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)

            projects.apply {
                implementation(core.common)
            }
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }

    androidLibrary {
        namespace = "com.saharapps.ui"
    }
}