package ru.vood.flow.abstraction.web

import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.web.reactive.function.client.WebClient

abstract class AbstractWebClient<T, R>(
    val iWebResponseMapper: IWebResponseMapper<R>,
    val enableLogging: Boolean = true,
) {

    abstract fun prepareRequest(rq: T): WebClient.RequestBodySpec


    suspend fun runRequest(rq: T): R {
        val exchangeToMono = prepareRequest(rq)
            .exchangeToMono { clRe -> clRe.toEntity(String::class.java) }

        val mono = if (enableLogging) {
            exchangeToMono
                .log()
        } else exchangeToMono

        return mono
            .map(iWebResponseMapper::map)
            .awaitSingle()

    }

}