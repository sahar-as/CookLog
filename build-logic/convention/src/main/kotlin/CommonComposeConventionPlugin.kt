import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.compose.ComposeExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class CommonComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        pluginManager.apply("org.jetbrains.compose")
        pluginManager.apply("org.jetbrains.kotlin.plugin.compose")

        val composeDeeps = extensions.getByType<ComposeExtension>().dependencies

        extensions.getByType<KotlinMultiplatformExtension>().apply {
            sourceSets.apply {
                commonMain.dependencies {
                    implementation(composeDeeps.runtime)
                    implementation(composeDeeps.foundation)
                    implementation(composeDeeps.material3)
                    implementation(composeDeeps.ui)
                    implementation(composeDeeps.components.resources)
                    implementation(composeDeeps.components.uiToolingPreview)
                }
            }
        }
    }
}