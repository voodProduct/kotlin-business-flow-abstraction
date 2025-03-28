package ru.vood.flow.abstraction.router.abstraction.byInOutClass

import ru.vood.flow.abstraction.router.abstraction.AbstractRouter

abstract class AbstractRouterByInOutClass<
        T : Any,
        R : Any,
        wId : IWorkerIdByInOutClass<T, R>,
        worker : IWorkerByInOutClass<T, R, wId>
        >(
    iWorkerList: List<worker>
) : AbstractRouter<T, R, wId, worker>(iWorkerList) {

    suspend inline fun <reified IT : T, reified IR : R> mapData(
        crossinline data: suspend () -> IT,
        idF: () -> wId
    ): IR {
        val validateMapperId = idF()
        return data().let { dataDto ->
            routedMap[validateMapperId]?.doWork(dataDto) as IR?
        } ?: error("For router ${this::class.java.canonicalName} not found worker with Id $validateMapperId")
    }
}