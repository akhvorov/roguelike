group = "com.rogue"
version = "1.0-SNAPSHOT"

plugins {
    id("tanvd.kosogor") version "1.0.4"
    application
    kotlin("jvm") version "1.3.21" apply true
}


repositories {
    jcenter()
}

dependencies {
    compile(kotlin("stdlib"))
    testCompile("org.junit.jupiter", "junit-jupiter-api", "5.2.0")
    testRuntime("org.junit.jupiter", "junit-jupiter-engine", "5.2.0")
}

application {
    mainClassName = "com.rogue.MainKt"
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
