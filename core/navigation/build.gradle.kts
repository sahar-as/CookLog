plugins {
    id("cooklog.android.library")
}

kotlin {
    initializeAndroidTarget()
    initializeFramework()

    androidLibrary {
        namespace = "com.saharapps.navigation"
    }
}
