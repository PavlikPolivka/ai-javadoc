plugins {
    id("java")
    id("org.jetbrains.intellij") version "1.17.4"
}

group = "com.ppolivka"
version = "1.0.1"

repositories {
    mavenCentral()
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

intellij {
    version.set("2023.3.7")
    plugins.set(listOf("com.intellij.java"))
}

dependencies {
    implementation("org.json:json:20240303")
}

tasks {
    buildSearchableOptions {
        enabled = false
    }

    patchPluginXml {
        version.set("${project.version}")
        sinceBuild.set("233")
        untilBuild.set("242.*")
    }
}