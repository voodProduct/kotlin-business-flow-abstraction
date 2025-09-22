plugins {
    kotlin("jvm") apply false
}

allprojects {
    group = "ru.vood.kotlin" // Укажите вашу группу
    version = "1.0.0" // Укажите версию

    repositories {
        mavenCentral()
        jcenter()
    }
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "maven-publish")

    // Общие настройки для всех подпроектов
    tasks.withType<Test> {
        useJUnitPlatform()
    }

    // Конфигурация публикации
    configure<PublishingExtension> {
        publications {
            create<MavenPublication>("mavenJava") {
                from(components["java"])
                groupId = project.group.toString()
                artifactId = project.name
                version = project.version.toString()

                pom {
                    name.set(project.name)
                    description.set("Description for ${project.name}")
                    url.set("https://github.com/voodProduct/kotlin-business-flow-abstraction.git")

                    licenses {
                        license {
                            name.set("The Apache License, Version 2.0")
                            url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                        }
                    }

                    developers {
                        developer {
                            id.set("igorvood")
                            name.set("Balitsky Igor")
                            email.set("balitsky_igor@list.ru")
                        }
                    }
                }
            }
        }

        repositories {
            mavenLocal()
        }
    }
}