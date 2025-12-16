package ru.vood.flow.abstraction.acync

import kotlinx.coroutines.*

object AsyncLightBatchOperations {

    inline infix fun <reified T> asyncRunBlockingBatch(batchData: () -> Iterable<T>): Iterable<T> =
        batchData()

    inline infix fun <reified T> Iterable<T>.onScope(crScope: CoroutineScope) = crScope to this

    inline infix fun <reified A, reified B> Iterable<A>.withFunction(crossinline funct: (A) -> B): Iterable<B> {
        val collection: Iterable<A> = this
        return runner(collection, funct)
    }

    inline infix fun <reified A, reified B> Pair<CoroutineScope, Iterable<A>>.withFunction(
        crossinline funct: (A) -> B
    ): Iterable<B> {
        val (crScope: CoroutineScope, collection: Iterable<A>) = this
        return runner(collection, funct, crScope)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    inline fun <reified A, reified B> runner(
        collection: Iterable<A>,
        crossinline funct: (A) -> B,
        crScope: CoroutineScope = CoroutineScope(Dispatchers.IO + Job()),
    ): Iterable<B> = runBlocking {
        val differed = collection
            .map { crScope.async { funct(it) } }
        //надо подождать пока все выполнится
        differed.awaitAll()

        // надо проверить есть ли исключения
        // getCompletionExceptionOrNull в текущей версии 1.4.10 помечен как эксперементальный,
        // если будут проблеммы обратить внимание
        differed.mapNotNull { it.getCompletionExceptionOrNull() }
            // есть исключения, надо добраться до истинного исключения, оно может быть обернуто CancellationException
            .forEach {
                when (it) {
                    is CancellationException -> throw it.cause!!
                    else -> throw it
                }
            }
        // исключений не было, формирование результата
        differed.awaitAll()
    }

}