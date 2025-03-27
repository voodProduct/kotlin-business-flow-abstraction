package ru.vood.flow

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.http.RequestEntity
import org.springframework.http.ResponseEntity

@SpringBootApplication
class AbstractionApplication

fun main(args: Array<String>) {
    runApplication<AbstractionApplication>(*args)
}
