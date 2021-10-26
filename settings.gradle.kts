pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()

        maven {
            name = "Fabric"
            url = uri("https://maven.fabricmc.net")
        }
    }
}

rootProject.name = "loom-quiltflower-mini"
includeBuild("build-logic")
