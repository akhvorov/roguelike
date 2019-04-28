import org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompile

group = "com.rogue"
version = "1.0-SNAPSHOT"

plugins {
    id("tanvd.kosogor") version "1.0.4" apply true
    application apply true
    kotlin("jvm") version "1.3.31" apply true
}


repositories {
    jcenter()
    maven("https://jitpack.io")
}

dependencies {
    compile(kotlin("stdlib"))

    compile("org.hexworks.zircon", "zircon.core-jvm", "2019.0.19-PREVIEW")
    compile("org.hexworks.zircon", "zircon.jvm.swing", "2019.0.19-PREVIEW")
    compile("org.jline", "jline", "3.5.1")
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
