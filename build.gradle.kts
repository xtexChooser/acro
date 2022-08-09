plugins {
    kotlin("jvm") version "1.7.10"
    kotlin("plugin.serialization") version "1.7.10"
    id("application")
    id("com.github.johnrengelman.shadow") version("7.1.2")
}

group = "acro"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven { setUrl("https://repo.opencollab.dev/maven-releases/") }
    //maven { setUrl("https://repo.opencollab.dev/maven-snapshots/") }
    maven { setUrl("https://jitpack.io/") }
    maven { setUrl("https://libraries.minecraft.net") }
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:1.4.0-RC")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-hocon:1.4.0-RC")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    implementation("com.squareup.okio:okio:3.2.0")
    implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.10")

    // Logging
    implementation("org.slf4j:slf4j-api:2.0.0-alpha7")
    implementation("org.apache.logging.log4j:log4j-core:2.18.0")
    implementation("org.apache.logging.log4j:log4j-api:2.18.0")
    implementation("org.apache.logging.log4j:log4j-slf4j18-impl:2.18.0")

    // Minecraft
    implementation("com.github.steveice10:MCProtocolLib:1.19-1")

    implementation("net.kyori:adventure-api:4.11.0")
    implementation("net.kyori:adventure-text-minimessage:4.11.0")
    implementation("net.kyori:adventure-extra-kotlin:4.11.0")

    // Database
    implementation("org.jetbrains.exposed:exposed-core:0.39.2")
    implementation("org.jetbrains.exposed:exposed-dao:0.39.2")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.39.2")
    implementation("org.postgresql:postgresql:42.4.1")
    implementation("com.h2database:h2:2.1.214")

    // AcroCTL
    implementation("com.hcyacg:tencent-guild-protocol:0.3.9")
    implementation("com.mojang:brigadier:1.0.18")
}

application {
    mainClass.set("acro.main.MainKt")
}
