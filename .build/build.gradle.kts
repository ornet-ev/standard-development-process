plugins {
    kotlin("jvm") version "2.1.20"
    application

    alias(libs.plugins.org.somda.repository.collection)
}

group = "org.somda"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.asciidoc.converter)
    implementation(libs.xmlschema2asciidoc)

    implementation(libs.log4j.api.kotlin)
    implementation(libs.log4j.core)

    implementation(libs.clikt)
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}

application {
    mainClass.set("org.somda.MainKt")
}