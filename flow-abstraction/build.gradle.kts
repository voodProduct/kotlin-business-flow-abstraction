plugins {
    kotlin("jvm")
//    kotlin("plugin.spring")
//    id("org.springframework.boot")
//    id("io.spring.dependency-management")
}

group = "ru.vood.flow"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {

    // https://mvnrepository.com/artifact/io.arrow-kt/arrow-core
    implementation(platform("io.arrow-kt:arrow-stack:2.0.1"))
    runtimeOnly("io.arrow-kt:arrow-core")
//    implementation("org.springframework.boot:spring-boot-starter-webflux")
//    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
//    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
//    implementation("org.jetbrains.kotlin:kotlin-reflect")
//    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
//    testImplementation("org.springframework.boot:spring-boot-starter-test")
//    testImplementation("io.projectreactor:reactor-test")
//    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
//    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test")
//    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
