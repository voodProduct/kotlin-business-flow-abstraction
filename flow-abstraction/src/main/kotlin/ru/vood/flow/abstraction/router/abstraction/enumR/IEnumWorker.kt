package ru.vood.flow.abstraction.router.abstraction.enumR

import ru.vood.flow.abstraction.router.abstraction.IWorker

interface IEnumWorker<
        in T : Any,
        out R : Any,
        E
        >
    : IWorker<T, R, E>
        where E : Enum<out E> {

    override suspend fun doWork(data: T, wId: E): R {
        return handle(data, wId)
    }

    fun handle(data: T, wId: E): R

}