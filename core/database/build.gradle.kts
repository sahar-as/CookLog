plugins {
    id("cooklog.android.library")
    id("cooklog.kmp.room")
    id("cooklog.cmp.koin")
}

kotlin {
    initializeAndroidTarget()
    initializeFramework()

    sourceSets {
        androidMain.dependencies {
            implementation(libs.koin.android)
        }
        commonMain.dependencies {
            implementation(libs.androidx.lifecycle.runtimeCompose)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }

    androidLibrary {
        namespace = "com.saharapps.database"
    }
}