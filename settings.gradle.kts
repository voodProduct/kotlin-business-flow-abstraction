pluginManagement {


    plugins {
        kotlin("jvm") version "2.1.21"
        kotlin("plugin.spring") version "2.1.21"
        kotlin("plugin.serialization") version "2.1.21"
        id("org.springframework.boot") version "3.5.0"
        id("io.spring.dependency-management") version "1.1.7"
        id("maven-publish") version "0.8.0" // Добавьте эту строку
    }

    repositories {
        jcenter()
        gradlePluginPortal()
        mavenCentral()
    }
}
rootProject.name = "ru.vood.kotlin" // Добавьте имя корневого проекта
rootProject.buildFileName = "build.gradle.kts"

//include(":example-app")
include(":flow-abstraction")
include(":test-util")
