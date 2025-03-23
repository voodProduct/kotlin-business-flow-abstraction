package ru.vood.flow.abstraction.router

import ru.vood.flow.abstraction.router.abstraction.IWorker

interface IHandler<TT : Any, RR : Any> : IWorker<HandlerId<TT, RR>> {

    override suspend fun <T, R> doWork(data: T): R {
        return handle(data as TT) as R
    }

    fun handle(data: TT): RR
}