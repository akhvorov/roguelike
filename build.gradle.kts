import org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompile

group = "com.rogue"
version = "1.0-SNAPSHOT"

buildscript {
    repositories { jcenter() }

    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-serialization:1.3.31")
    }
}

plugins {
    id("tanvd.kosogor") version "1.0.4" apply true
    application apply true
    kotlin("jvm") version "1.3.31" apply true
    id("kotlinx-serialization") version "1.3.31" apply true
}


repositories {
    jcenter()
    maven("https://jitpack.io")
}

dependencies {
    compile(kotlin("stdlib"))

    compile("org.hexworks.zircon", "zircon.core-jvm", "2019.0.19-PREVIEW")
    compile("org.hexworks.zircon", "zircon.jvm.swing", "2019.0.19-PREVIEW")

    compile("org.jetbrains.kotlinx:kotlinx-serialization-runtime:0.11.0")

    compile("com.fasterxml.jackson.core", "jackson-core", "2.9.7")
    compile("com.fasterxml.jackson.core", "jackson-databind", "2.9.7")
    compile("com.fasterxml.jackson.module", "jackson-module-kotlin", "2.9.7")
    compile("org.junit.jupiter", "junit-jupiter-api", "5.0.2")
}

application {
    mainClassName = "com.rogue.MainKt"
}

tasks.withType<KotlinJvmCompile> {
    kotlinOptions {
        languageVersion = "1.3"
        apiVersion = "1.3"

        jvmTarget = "1.8"
    }
}

tasks.withType<JavaExec> {
    standardInput = System.`in`
    standardOutput = System.out
}

tasks.withType<Test> {
    useJUnitPlatform()

    testLogging {
        events("passed", "skipped", "failed")
    }
}
