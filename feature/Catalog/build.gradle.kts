plugins {
    id("cooklog.android.application")
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
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}


android {
    namespace = "com.saharapps.catalog"
}
