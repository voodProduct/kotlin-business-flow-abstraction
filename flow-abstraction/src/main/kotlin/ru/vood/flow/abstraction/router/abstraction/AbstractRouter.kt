package ru.vood.flow.abstraction.router.abstraction

abstract class AbstractRouter<
        T:Any,
        R:Any,
        WORKER_ID : IWorkerId,
        I_WORKER : IWorker<T, R, WORKER_ID>
        >(
    iWorkerList: List<I_WORKER>
) {
    val routedMap = collectAndValidateMeta(iWorkerList)

    private fun collectAndValidateMeta(handlers: List<IWorker<T, R, WORKER_ID>>): Map<WORKER_ID, IWorker<T, R, WORKER_ID>> {
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
/*
     suspend inline fun <reified IT: T, reified IR: R> route(
         crossinline data: suspend () -> IT,
         workerIdExtractor: (IT) -> WORKER_ID
    ): R {
        val dataDto = data()
        val workerId = workerIdExtractor(dataDto)
        val result: R = routedMap[workerId]?.doWork(dataDto)
            ?: error("For router ${this::class.java.canonicalName} not found worker with Id $workerId")
        return result
    }
*/

}