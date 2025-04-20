package ru.vood.flow.abstraction.router.abstraction

interface IWorker<
        in T : Any,
        out R : Any,
         WORKER_ID
        > {
    val workerId: Set<WORKER_ID>
    suspend fun doWork(data: T, wId: WORKER_ID): R
}