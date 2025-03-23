package ru.vood.flow.abstraction.router.abstraction

abstract class AbstractRouter<rID : IWorkerId, Routed : IWorker<rID>>(
    iRoutedList: List<Routed>
) {
     val routedMap = collectAndValidateMeta(iRoutedList)

    private fun collectAndValidateMeta(handlers: List<IWorker<rID>>): Map<rID, IWorker<rID>> {
        val prepared = handlers
            .map { it.workerId to it }

        val groupByForAssertionError = prepared.groupBy { it.first }
            .filter { it.value.size > 1 }
            .map { it.key to it.value.map { q -> q.second.javaClass.canonicalName to q.first } }
            .toMap()

        require(groupByForAssertionError.isEmpty()) {
            "For router ${this::class.java.canonicalName} found several workers with same Id $groupByForAssertionError"
        }

        return prepared.toMap()
    }

     suspend inline fun <reified T, reified R> route(
        crossinline data: suspend () -> T,
        workerIdExtractor: (T) -> rID
    ): R {
        val dataDto = data()
        val workerId = workerIdExtractor(dataDto)
        val result: R = routedMap[workerId]?.doWork<T, R>(dataDto)
            ?: error("For router ${this::class.java.canonicalName} not found worker with Id $workerId")
        return result
    }
}