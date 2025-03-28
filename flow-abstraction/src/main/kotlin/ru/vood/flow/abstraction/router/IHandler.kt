package ru.vood.flow.abstraction.router

import ru.vood.flow.abstraction.router.abstraction.IWorker

interface IHandler<
        T : Any,
        R : Any> :
    IWorker<T, R, HandlerId<T, R>> {

    override suspend fun doWork(data: T): R {
        return handle(data)
    }

    fun handle(data: T): R
}