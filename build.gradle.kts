plugins {
    kotlin("jvm") version "1.7.10"
    kotlin("plugin.serialization") version "1.7.10"
    id("application")
}

group = "acro"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven { setUrl("https://jitpack.io") }
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:1.4.0-RC")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-hocon:1.4.0-RC")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    implementation("com.squareup.okio:okio:3.2.0")

    implementation("org.slf4j:slf4j-api:2.0.0-alpha7")
    implementation("org.slf4j:slf4j-simple:2.0.0-alpha7")

    implementation("com.github.GeyserMC:MCProtocolLib:1.16.5-2") {
        exclude(group = "com.github.steveice10")
    }
    implementation("com.github.GeyserMC:OpenNBT:1.4")
    implementation("com.github.GeyserMC:mcauthlib:1.4")
    implementation("com.github.GeyserMC:PacketLib:2.0")
}

application {
    mainClass.set("acro.main.MainKt")
}
