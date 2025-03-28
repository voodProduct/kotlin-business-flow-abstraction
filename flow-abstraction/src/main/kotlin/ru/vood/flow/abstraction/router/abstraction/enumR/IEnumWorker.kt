package ru.vood.flow.abstraction.router.abstraction.enumR

import ru.vood.flow.abstraction.router.abstraction.IWorker

interface IEnumWorker<
        in T : Any,
        out R : Any,
        out E : Enum<out E>>
    : IWorker<T, R, EnumWorkerId<out E>> {

    override suspend fun doWork(data: T): R {
        return handle(data as T) as R
    }

    //    override suspend fun <T, R> doWork(data: T): R {
//        print(this::class.java.canonicalName+" -> ")
//        return handle(data as TT) as R
//    }

    fun handle(data: T): R

}