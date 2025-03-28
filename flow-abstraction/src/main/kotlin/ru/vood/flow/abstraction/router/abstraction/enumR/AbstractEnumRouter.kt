package ru.vood.flow.abstraction.router.abstraction.enumR

import ru.vood.flow.abstraction.router.abstraction.AbstractRouter
import ru.vood.flow.abstraction.router.abstraction.IWorker
import kotlin.enums.EnumEntries

abstract class AbstractEnumRouter<T : Any, R : Any, E : Enum<E>>(
    iWorkerList: List<IWorker<T, R, EnumWorkerId<E>>>,
    eVals: EnumEntries<E>,
) : AbstractRouter<
        T,
        R,
        EnumWorkerId<E>,
        IWorker<T, R, EnumWorkerId<E>>
        >(iWorkerList) {

    init {
        val workerIEnumWorker = iWorkerList.map { it.workerId.emun }.toSet()
        val filter = eVals.filter { !workerIEnumWorker.contains(it) }
        require(filter.isEmpty()){"Not found implementation ${IEnumWorker::class.java.canonicalName} for next EnumValues $filter of Enum type ${eVals.first()::class.java.canonicalName}"}
    }

    suspend inline fun <reified IT : T, reified IR : R> mapData(
        crossinline data: suspend () -> IT,
        enumF: () -> E
    ): R {
        val validateMapperId = EnumWorkerId(enumF())
        return data().let { dataDto ->
            routedMap[validateMapperId]?.doWork(dataDto)
        } ?: error("For router ${this::class.java.canonicalName} not found worker with Id $validateMapperId")
    }
}