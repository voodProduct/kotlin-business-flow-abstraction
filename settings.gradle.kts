pluginManagement {


    plugins {
        kotlin("jvm") version "2.1.20"
        kotlin("plugin.spring") version "2.1.20"
        id("org.springframework.boot") version "3.5.0"
        id("io.spring.dependency-management") version "1.1.7"
    }

    repositories {
        jcenter()
        gradlePluginPortal()
        mavenCentral()
    }
}

//include(":example-app")
include(":flow-abstraction")
