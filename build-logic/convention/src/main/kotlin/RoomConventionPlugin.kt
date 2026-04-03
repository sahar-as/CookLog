import androidx.room.gradle.RoomExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class RoomConventionPlugin : Plugin<Project>{
    override fun apply(target: Project) {
        with(target){
            with(pluginManager){
                apply("com.google.devtools.ksp")
                apply("androidx.room")
            }
            extensions.configure<RoomExtension> {
                schemaDirectory("$projectDir/schemas")
            }

            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            val roomCompiler = libs.findLibrary("androidx-room-compiler").get()
            val roomRuntime = libs.findLibrary("androidx-room-runtime").get()

            plugins.withId("org.jetbrains.kotlin.multiplatform") {
                afterEvaluate {
                    dependencies {
                        add("kspCommonMainMetadata", roomCompiler)
                        add("commonMainImplementation", roomRuntime)

                        listOf(
                            "kspAndroid",
                            "kspIosX64",
                            "kspIosArm64",
                            "kspIosSimulatorArm64"
                        ).forEach { config ->
                            configurations.findByName(config)?.let {
                                add(config, roomCompiler)
                            }
                        }
                    }
                }
            }
        }
    }
}