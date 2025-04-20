package ru.vood.flow.abstraction.router.abstraction

interface IRouter<
        T : Any,
        R : Any,
        WORKER_ID,
        I_WORKER : IWorker<T, R, WORKER_ID>
        > {

    val routedMap: Map<WORKER_ID, IWorker<T, R, WORKER_ID>>

    fun collectAndValidateMeta(handlers: List<IWorker<T, R, WORKER_ID>>): Map<WORKER_ID, IWorker<T, R, WORKER_ID>> {
        val prepared = handlers
            .flatMap { iWorker -> iWorker.workerId.map { wId -> wId to iWorker } }

        val groupByForAssertionError = prepared.groupBy { it.first }
            .filter { it.value.size > 1 }
            .map { it.key to it.value.map { q -> q.second.javaClass.canonicalName to q.first } }
            .toMap()

        require(groupByForAssertionError.isEmpty()) {
            "For router ${this::class.java.canonicalName} found several workers with same Id $groupByForAssertionError"
        }

        return prepared.toMap()
    }

}