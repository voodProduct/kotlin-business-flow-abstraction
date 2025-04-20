package ru.vood.flow.abstraction.router.abstraction.byInClass

import ru.vood.flow.abstraction.router.abstraction.AbstractRouter

abstract class AbstractRouterByInClass<
        T : Any,
        R : Any,
        wId : IWorkerIdByInClass<T>,
        worker : IWorkerByInClass<T, R, wId>
        >(
    iWorkerList: List<worker>
) : AbstractRouter<T, R, wId, worker>(iWorkerList) {

    suspend inline fun <reified IT : T, reified IR : R> mapData(
        crossinline data: suspend () -> IT,
        idF: () -> wId
    ): IR {
        val validateMapperId = idF()
        return data().let { dataDto ->
            routedMap[validateMapperId]?.doWork(dataDto, validateMapperId) as IR?
        } ?: error("For router ${this::class.java.canonicalName} not found worker with Id $validateMapperId")
    }
}