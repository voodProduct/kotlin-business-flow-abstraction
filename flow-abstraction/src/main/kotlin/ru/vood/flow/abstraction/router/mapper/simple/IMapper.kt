package ru.vood.flow.abstraction.router.mapper.simple

import ru.vood.flow.abstraction.router.abstraction.byInOutClass.IWorkerByInOutClass

interface IMapper<TT : Any, out RR : Any> :
    IWorkerByInOutClass<TT, RR, MapperId<TT, RR>> {

    override suspend fun doWork(data: TT): RR {
        print(this::class.java.canonicalName + " -> ")
        return handle(data)
    }

    fun handle(data: TT): RR


}