package ru.vood.flow.abstraction.router.enumR

import ru.vood.flow.abstraction.router.abstraction.IWorker

interface IEnumWorker<TT : Any, out RR : Any, E : Enum<E>>: IWorker<OnlyEnumId<E>> {

    override suspend fun <T, R> doWork(data: T): R {
        print(this::class.java.canonicalName+" -> ")
        return handle(data as TT) as R
    }

    fun handle(data: TT): RR

}