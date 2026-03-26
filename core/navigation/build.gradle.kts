plugins {
    id("cooklog.android.library")
}

kotlin {
    initializeAndroidTarget()
    initializeFramework()

    sourceSets {
        commonMain.dependencies {
        }
        commonTest.dependencies {
        }
    }

    androidLibrary {
        namespace = "com.saharapps.navigation"
    }
}
