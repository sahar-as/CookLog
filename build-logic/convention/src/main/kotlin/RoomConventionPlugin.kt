import androidx.room.gradle.RoomExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class RoomConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            // 1. Apply necessary plugins
            pluginManager.apply("com.google.devtools.ksp")
            pluginManager.apply("androidx.room")

            // 2. Configure Room
            extensions.configure<RoomExtension> {
                schemaDirectory("$projectDir/schemas")
            }

            // 3. Setup Dependencies using Version Catalog
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
            val roomCompiler = libs.findLibrary("androidx-room-compiler").get()
            val roomRuntime = libs.findLibrary("androidx-room-runtime").get()
            val sqliteBundled = libs.findLibrary("androidx-sqlite-bundled").get()

            // 4. Configure KMP SourceSets and KSP
            extensions.configure<KotlinMultiplatformExtension> {
                sourceSets.getByName("commonMain").dependencies {
                    implementation(roomRuntime)
                    implementation(sqliteBundled)
                }
            }

            // 5. Apply KSP to all relevant targets
            dependencies.apply {
                // Common Metadata KSP
                add("kspCommonMainMetadata", roomCompiler)

                // Loop through targets to apply target-specific KSP
                // This is safer than hardcoded strings if you add more targets later
                afterEvaluate {
                    val kspTargets = listOf(
                        "kspAndroid",
                        "kspIosX64",
                        "kspIosArm64",
                        "kspIosSimulatorArm64"
                    )
                    kspTargets.forEach { config ->
                        if (configurations.findByName(config) != null) {
                            add(config, roomCompiler)
                        }
                    }
                }
            }
        }
    }
}





//import androidx.room.gradle.RoomExtension
//import org.gradle.api.Plugin
//import org.gradle.api.Project
//import org.gradle.api.artifacts.VersionCatalogsExtension
//import org.gradle.kotlin.dsl.configure
//import org.gradle.kotlin.dsl.dependencies
//import org.gradle.kotlin.dsl.getByType
//
//class RoomConventionPlugin : Plugin<Project> {
//    override fun apply(target: Project) {
//        with(target) {
//            with(pluginManager) {
//                apply("com.google.devtools.ksp")
//                apply("androidx.room")
//            }
//
//            extensions.configure<RoomExtension> {
//                schemaDirectory("$projectDir/schemas")
//            }
//
//            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
//            val roomCompiler = libs.findLibrary("androidx-room-compiler").get()
//            val roomRuntime = libs.findLibrary("androidx-room-runtime").get()
//            val sqliteBundled = libs.findLibrary("androidx-sqlite-bundled").get()
//
//            afterEvaluate {
//                dependencies {
//                    add("commonMainImplementation", roomRuntime)
//                    add("commonMainImplementation", sqliteBundled)
//                    add("kspCommonMainMetadata", roomCompiler)
//
//                    val kspTargets = listOf(
//                        "kspAndroid",
//                        "kspIosX64",
//                        "kspIosArm64",
//                        "kspIosSimulatorArm64"
//                    )
//
//                    kspTargets.forEach { config ->
//                        if (configurations.findByName(config) != null) {
//                            add(config, roomCompiler)
//                        }
//                    }
//                }
//            }
//        }
//    }
//}