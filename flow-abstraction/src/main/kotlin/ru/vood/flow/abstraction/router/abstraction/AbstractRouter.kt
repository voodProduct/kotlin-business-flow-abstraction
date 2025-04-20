package ru.vood.flow.abstraction.router.abstraction

abstract class AbstractRouter<
        T : Any,
        R : Any,
        WORKER_ID : IWorkerId,
        I_WORKER : IWorker<T, R, WORKER_ID>
        >(
    iWorkerList: List<I_WORKER>
) : IRouter<T, R, WORKER_ID, I_WORKER> {
    override val routedMap: Map<WORKER_ID, IWorker<T, R, WORKER_ID>> = this.collectAndValidateMeta(iWorkerList)


    suspend inline fun <reified IT : T, reified IR : R> route(
        crossinline data: suspend () -> IT,
        idF: () -> WORKER_ID
    ): R {
        val workerId = idF()
        val let = data().let { dataDto ->
            routedMap[workerId]?.doWork(dataDto, workerId)
        }   ?: error("For router ${this::class.java.canonicalName} not found worker with Id $workerId")
        return let
    }


}