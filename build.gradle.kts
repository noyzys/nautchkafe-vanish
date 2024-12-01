plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "dev.nautchkafe.vanish"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/public/") 
}

dependencies {
    // Spigot API
    implementation("org.spigotmc:spigot-api:1.21-R0.1-SNAPSHOT")

    // Kyori Adventure API 
    implementation("net.kyori:adventure-api:4.13.0")
    implementation("net.kyori:adventure-text-minimessage:4.13.0")
}

tasks {
    shadowJar {
        archiveBaseName.set("vanish-plugin")
        archiveClassifier.set("")
        archiveVersion.set(version)
    }
    
    run {
        jvmArgs = listOf("-Xmx1024m", "-Xms1024m") 
    }
}