plugins {
    `groovy-gradle-plugin`
}

repositories {
    mavenCentral()
    maven("https://maven.fabricmc.net")
}

dependencies {
    val asm = "9.2"
    implementation("org.ow2.asm:asm:$asm")
    implementation("org.ow2.asm:asm-commons:$asm")
    implementation("net.fabricmc:tiny-remapper:0.7.0")
}
