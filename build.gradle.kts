group = "com.rogue"
version = "1.0-SNAPSHOT"

plugins {
    id("tanvd.kosogor") version "1.0.4" apply true
    application apply true
    kotlin("jvm") version "1.3.31" apply true
}


repositories {
    jcenter()
}

dependencies {
    compile(kotlin("stdlib"))
    compile("org.jline", "jline", "3.5.1")
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
