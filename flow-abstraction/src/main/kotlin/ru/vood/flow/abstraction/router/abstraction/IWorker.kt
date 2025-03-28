package ru.vood.flow.abstraction.router.abstraction

interface IWorker<
        in T : Any,
        out R : Any,
        out rId : IWorkerId
        > {
    val workerId: rId
    suspend fun doWork(data: T): R
}