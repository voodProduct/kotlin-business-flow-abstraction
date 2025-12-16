plugins {
    kotlin("jvm")
}

group = "ru.vood.flow"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

repositories {
    mavenCentral()
}

//val springVersion = "7.0.2"
val springVersion = "6.2.11"

//val kotestVersion = "6.0.7"
val kotestVersion = "5.9.1"
val arrangerVersion = "1.7.0"

dependencies {

    // https://mvnrepository.com/artifact/io.arrow-kt/arrow-core
    implementation(platform("io.arrow-kt:arrow-stack:2.2.0"))
    implementation(platform("org.jetbrains.kotlinx:kotlinx-coroutines-bom:1.10.2"))
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

    implementation("org.springframework:spring-web:$springVersion")
    implementation("org.springframework:spring-webflux:$springVersion")
    implementation("org.springframework:spring-tx:$springVersion")
    implementation("org.slf4j:slf4j-api:2.0.17")
    api("io.arrow-kt:arrow-core-jvm:2.1.1")

    testImplementation(kotlin("test"))


    testImplementation(platform("io.kotest:kotest-bom:$kotestVersion"))
    testImplementation("io.kotest:kotest-framework-api-jvm")
    testImplementation("io.kotest:kotest-runner-junit5")
    testImplementation("com.ocadotechnology.gembus:test-arranger:$arrangerVersion")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
