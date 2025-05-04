package ru.vood.flow.abstraction.web

import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

/**
 * Класс-обёртка над клиентом Spring WebFlux, предоставляющий возможность унифицированного обращения к внешним сервисам.
 *
 * Позволяет настраивать подготовку запросов ([prepareRequest]) и обработку ответов с использованием заданного маппера
 * типа [IWebResponseMapper]. Поддерживает логгирование операций, если это разрешено настройкой [enableLogging].
 *
 * @param iWebResponseMapper Объект-маппер, используемый для преобразования полученного строкового ответа в требуемый тип результата.
 * @param enableLogging Флаг, определяющий необходимость ведения логов операций клиентского взаимодействия.
 */
abstract class AbstractWebClient<T, R>(
    /**
     * Маппер, выполняющий преобразование строки-ответа от сервера в объект необходимого типа.
     */
    val iWebResponseMapper: IWebResponseMapper<R>,
    /**
     * Признак включения логирования сетевых взаимодействий клиента.
     */
    val enableLogging: Boolean = true,
) {

    /**
     * Подготавливает спецификацию запроса для отправки серверу.
     *
     * Этот метод предназначен для переопределения в наследниках класса, чтобы настроить конкретные детали каждого запроса.
     *
     * @param rq Запрос, передаваемый в клиент, согласно типу параметра [T].
     * @return Спецификация запроса, готовая к отправке на удалённый сервер.
     */
    abstract fun prepareRequest(rq: T): WebClient.RequestBodySpec

    /**
     * Отправляет подготовленный запрос и возвращает обработанный результат в виде ожидаемого типа [R].
     *
     * Выполнение асинхронное благодаря использованию механизма ко-рутин, доступному через библиотеку Coroutine-Reactor.
     *
     * @param rq Запрос, который должен быть отправлен серверу.
     * @return Обработанный результат запроса в виде объекта типа [R], возвращённого маппером.
     */
    suspend fun runRequest(rq: T): R {
        return prepareRequest(rq)
            .exchangeToMono { clRe -> clRe.toEntity(String::class.java) }
            .alsoIfEnabledLog()
            .map(iWebResponseMapper::map)
            .awaitSingle()
    }

    /**
     * Применяет логирование к моно-публикатору, если флаг [enableLogging] установлен в значение `true`.
     *
     * Если логирование отключено, метод вернёт исходный публикацию без изменений.
     *
     * @return Публикация, дополненная возможностью логирования, либо исходная публикация, если логирование отключено.
     */
    private fun <R> Mono<R>.alsoIfEnabledLog(): Mono<R> =
        if (enableLogging) log() else this

}