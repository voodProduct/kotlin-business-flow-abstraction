package ru.vood.flow.abstraction.router.abstraction.enumR

import ru.vood.flow.abstraction.router.abstraction.IWorker

interface IEnumWorker<
        in T : Any,
        out R : Any,
        out E
        >
    : IWorker<T, R, E>
        where E : Enum<out E>,
              E : IEnumWorkerId<out E> {

    override suspend fun doWork(data: T): R {
        return handle(data)
    }

    fun handle(data: T): R

}