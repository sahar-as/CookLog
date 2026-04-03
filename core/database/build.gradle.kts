plugins {
    id("cooklog.android.library")
}

kotlin {
    initializeAndroidTarget()
    initializeFramework()

    sourceSets {
        androidMain.dependencies {
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