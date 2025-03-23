package ru.vood.flow.abstraction.router.abstraction

interface IWorker<rId: IWorkerId> {
    val workerId: rId

    suspend fun<T, R> doWork(data: T): R
}