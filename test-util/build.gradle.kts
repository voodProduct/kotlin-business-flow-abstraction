plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
}

group = "ru.vood.kotlin"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

repositories {
    mavenCentral()
}

val jupiterVersion = "5.13.4"
val kotestVersion = "5.9.1"
val arrangerVersion = "1.6.4.1"
dependencies {
    implementation(project(":flow-abstraction"))
    implementation(platform("io.kotest:kotest-bom:$kotestVersion"))

    implementation("io.kotest:kotest-runner-junit5")
    implementation("io.kotest:kotest-framework-datatest")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    implementation("org.reflections:reflections:0.10.2")

    implementation("org.junit.jupiter:junit-jupiter-engine:$jupiterVersion")
    implementation("org.junit.jupiter:junit-jupiter-params:$jupiterVersion")

    implementation("com.ocadotechnology.gembus:test-arranger:$arrangerVersion")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
