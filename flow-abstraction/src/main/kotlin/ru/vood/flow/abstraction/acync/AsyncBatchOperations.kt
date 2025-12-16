package ru.vood.flow.abstraction.acync

import arrow.core.Either
import kotlinx.coroutines.*
import java.util.function.BiConsumer
import java.util.function.Function

/** Обрабатываемое значение
 * value - значение, которое нужно обработать
 * timeout - таймаут в миллисекундах, на обработку этого значения
 * reprocessAttempts - количество попыток обработки значения
 * */
data class AsyncValue<out T>(
    val value: T,
    val timeout: Long = DEFAULT_TIMEOUT,
    val reprocessAttempts: Int = DEFAULT_REPROCESS_ATTEMPT_COUNT
) {
    companion object {
        const val DEFAULT_TIMEOUT = 1000L
        const val DEFAULT_REPROCESS_ATTEMPT_COUNT = 0
    }
}

/** задача обработки значения
 * value - значение, которое нужно обработать
 * timeout - таймаут в миллисекундах, на обработку этого значения
 * attemptsLeft - количество оставшихся попыток обработки значения
 * fn - функция обработки значения
 * */
data class AsyncTask<T, R>(
    val value: T,
    val timeout: Long = AsyncValue.DEFAULT_TIMEOUT,
    val attemptsLeft: Int = AsyncValue.DEFAULT_REPROCESS_ATTEMPT_COUNT,
    val fn: suspend (T) -> R
)

/** функция запуска репроцессинга,
 * возвращает логику, если true - репроцессинг запускается
 * */
typealias ReprocessCondition = (Throwable) -> Boolean


/** Абстракция для обработки значений в параллельном потоке
 *  T - тип обрабатываемого значения
 *  R - тип возвращаемого результата
 *  AGG - тип результата
 *  doOnFail - функция обработки ошибки, может быть пустой. Служит для side эффектов, может слунапример для логгирования
 *  doOnSuccess - функция обработки результата без ошибки, может быть пустой. Служит для side эффектов, может слунапример для логгирования
 *  resultCombiner - функция слияния результата
 * */
class AsyncBatchOperations<T, R, out AGG>(
    private val doOnFail: BiConsumer<in T, Throwable>,
    private val doOnSuccess: BiConsumer<in T, in R>,
    private val resultCombiner: Function<Map<T, Either<Throwable, R>>, out AGG>
) {

    private val job = SupervisorJob()
    private val crScope = CoroutineScope(Dispatchers.IO + job)


    /**
     * Зпуск обработки значений в параллельном потоке
     * T - тип обрабатываемого значения
     * batch - список обрабатываемых значений,
     * reprocessCondition - функция запуска репроцессинга
     * work - функция обработки значения, запускается для каждого значения в отдельной корутинке асинхронно
     * */
    fun applyBatchOfValues(
        batch: Iterable<AsyncValue<T>>,
        reprocessCondition: ReprocessCondition = DEFAULT_REPROCESS_CONDITION,
        work: suspend (T) -> R
    ): AGG {
        return runBlocking {
            val result = doTasks(
                crScope,
                batch.map { t -> AsyncTask(t.value, t.timeout, t.reprocessAttempts, work) },
                reprocessCondition
            )

            resultCombiner.apply(
                result.map { either ->
                    either.fold(
                        { throw IllegalStateException("async batch not finished") },
                        { it }
                    )
                }.toMap()
            )
        }
    }

    /**
     * Зпуск обработки значений в параллельном потоке
     * T - тип обрабатываемого значения
     * batch - список обрабатываемых значений,
     * timeout - таймаут в миллисекундах, на обработку одного значения
     * reprocessAttempts - количество попыток обработки значения
     * reprocessCondition - функция запуска репроцессинга
     * work - функция обработки значения, запускается для каждого значения в отдельной корутинке асинхронно
     * */
    fun applyBatchOfValues(
        batch: Iterable<T>,
        timeout: Long = AsyncValue.DEFAULT_TIMEOUT,
        reprocessAttempts: Int = AsyncValue.DEFAULT_REPROCESS_ATTEMPT_COUNT,
        reprocessCondition: ReprocessCondition = DEFAULT_REPROCESS_CONDITION,
        work: suspend (T) -> R
    ): AGG {
        return runBlocking {
            val result = doTasks(
                crScope,
                batch.map { AsyncTask(it, timeout, reprocessAttempts, work) },
                reprocessCondition
            )

            resultCombiner.apply(
                result.map { either ->
                    either.fold(
                        { throw IllegalStateException("async batch not finished") },
                        { it }
                    )
                }.toMap()
            )
        }
    }

    private suspend fun doTasks(
        scope: CoroutineScope,
        asyncTaskList: List<AsyncTask<T, R>>,
        reprocessCondition: ReprocessCondition = DEFAULT_REPROCESS_CONDITION
    ): List<Either<AsyncTask<T, R>, Pair<T, Either<Throwable, R>>>> {
        if (asyncTaskList.isEmpty()) {
            return listOf()
        }
        val calc =
            asyncTaskList.map { task ->
                scope.async {
                    try {
                        val r = withTimeout(task.timeout) {
                            task.fn(task.value)
                        }
                        doOnSuccess.accept(task.value, r)
                        return@async task.value to Either.Right(r)
                    } catch (e: Exception) {
                        doOnFail.accept(task.value, e)
                        return@async task.value to Either.Left<Throwable>(e)
                    }
                }
            }
                .awaitAll()
                .zip(asyncTaskList)
                .map { resWithTask ->
                    when (val tryR = resWithTask.first.second) {
                        is Either.Right -> Either.Right(resWithTask.first)
                        is Either.Left ->
                            if (resWithTask.second.attemptsLeft <= 0 || !reprocessCondition(tryR.value))
                                Either.Right(resWithTask.first.first to Either.Left<Throwable>(tryR.value))
                            else with(resWithTask.second) {
                                Either.Left(AsyncTask(this.value, this.timeout, this.attemptsLeft - 1, this.fn))
                            }
                    }
                }
        val intermediate = calc.partition { it.fold({ false }, { true }) }
        val finishing = intermediate.second.map { either ->
            either.fold({ it }, { throw IllegalStateException("async either not partitioned") })
        }
        return intermediate.first + doTasks(scope, finishing, reprocessCondition)
    }

    companion object {
//        val LOGGER: Logger = LoggerFactory.getLogger(AsyncValue::class.java)

        val DEFAULT_REPROCESS_CONDITION: ReprocessCondition = { it is TimeoutCancellationException }

    }

}