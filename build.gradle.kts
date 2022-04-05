plugins {
    `java-gradle-plugin`
    `maven-publish`
    id("com.gradle.plugin-publish") version "0.16.0"
    id("build-logic")
}

group = "io.github.juuxel"
version = "1.3.0"

val bundle by configurations.creating {
    isCanBeResolved = true
    isCanBeConsumed = false
}

configurations {
    compileClasspath {
        extendsFrom(bundle)
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_16
    targetCompatibility = JavaVersion.VERSION_16
}

if (file("private.gradle").exists()) {
    apply(from = "private.gradle")
}

repositories {
    mavenCentral()
    maven("https://maven.fabricmc.net")
    maven("https://maven.quiltmc.org/repository/release")
}

dependencies {
    bundle(lqfMiniLogic.quiltflower())
    implementation("net.fabricmc:mapping-io:0.3.0")
    implementation("org.ow2.asm:asm:9.2")
    implementation("net.fabricmc:stitch:0.6.1")
    compileOnly("net.fabricmc:fabric-loom:0.10.42")
    compileOnly("org.jetbrains:annotations:22.0.0")
}

tasks {
    jar {
        bundle.resolve().forEach {
            from(zipTree(it))
        }
    }
}

gradlePlugin {
    plugins.register("lqfMiniPlugin") {
        id = "io.github.juuxel.loom-quiltflower-mini"
        implementationClass = "juuxel.loomquiltflowermini.LqfMiniPlugin"
    }
}

pluginBundle {
    website = "https://github.com/Juuxel/loom-quiltflower-mini"
    vcsUrl = "https://github.com/Juuxel/loom-quiltflower-mini"
    description = "Adds the Quiltflower decompiler to Minecraft mod projects using Fabric Loom 0.10."
    tags = listOf("minecraft", "fabric", "decompilers")

    plugins.named("lqfMiniPlugin") {
        displayName = "loom-quiltflower-mini"
    }
}

if (project.hasProperty("mavenUrl")) {
    publishing {
        repositories {
            maven {
                url = uri(project.property("mavenUrl").toString())
                credentials {
                    username = project.property("mavenUsername").toString()
                    password = project.property("mavenPassword").toString()
                }
            }
        }
    }
}
