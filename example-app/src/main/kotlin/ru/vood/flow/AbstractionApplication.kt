package ru.vood.flow

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AbstractionApplication

fun main(args: Array<String>) {
    runApplication<AbstractionApplication>(*args)
}
