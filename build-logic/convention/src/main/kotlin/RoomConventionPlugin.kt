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
            pluginManager.apply("com.google.devtools.ksp")
            pluginManager.apply("androidx.room")

            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
            val roomCompiler = libs.findLibrary("androidx-room-compiler").get()
            val roomRuntime = libs.findLibrary("androidx-room-runtime").get()
            val sqliteBundled = libs.findLibrary("androidx-sqlite-bundled").get()

            extensions.configure<RoomExtension> {
                schemaDirectory("$projectDir/schemas")
            }

            extensions.configure<KotlinMultiplatformExtension> {
                sourceSets.getByName("commonMain").dependencies {
                    implementation(roomRuntime)
                    implementation(sqliteBundled)
                }

                // Proper KSP targeting for KMP
                targets.configureEach {
                    val targetName = name.replaceFirstChar { it.uppercase() }
                    if (name == "metadata") {
                        configurations.findByName("kspCommonMainMetadata")?.let {
                            dependencies.add(it.name, roomCompiler)
                        }
                    } else {
                        configurations.findByName("ksp$targetName")?.let {
                            dependencies.add(it.name, roomCompiler)
                        }
                    }
                }
            }
        }
    }
}