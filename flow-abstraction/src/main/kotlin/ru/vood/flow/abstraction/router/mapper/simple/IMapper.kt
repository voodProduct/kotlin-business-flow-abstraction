package ru.vood.flow.abstraction.router.mapper.simple

import ru.vood.flow.abstraction.router.abstraction.byInOutClass.IWorkerByInOutClass

interface IMapper<TT : Any, out RR : Any> :
    IWorkerByInOutClass<TT, RR, MapperId<TT, RR>> {

    override suspend fun <T, R> doWork(data: T): R {
        print(this::class.java.canonicalName+" -> ")
        return handle(data as TT) as R
    }

    fun handle(data: TT): RR


}